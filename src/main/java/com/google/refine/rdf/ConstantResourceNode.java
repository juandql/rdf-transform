package com.google.refine.rdf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.google.refine.model.Project;

import org.eclipse.rdf4j.common.net.ParsedIRI;
import org.eclipse.rdf4j.model.Value;
import org.eclipse.rdf4j.model.ValueFactory;
import org.eclipse.rdf4j.repository.RepositoryConnection;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonGenerationException;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

public class ConstantResourceNode extends ResourceNode implements ConstantNode {
    // private final static Logger logger = LoggerFactory.getLogger("RDFT:ConstResNode");

    static private final String strNODETYPE = "resource";

    private final String strIRI;

    @JsonCreator
    public ConstantResourceNode( @JsonProperty("value") String strIRI ) {
        this.strIRI = Util.toSpaceStrippedString(strIRI);
    }

    static String getNODETYPE() {
        return ConstantResourceNode.strNODETYPE;
    }

	@Override
	public String getNodeName() {
		return this.strIRI;
	}

	@Override
	public String getNodeType() {
		return ConstantResourceNode.strNODETYPE;
	}

    @JsonProperty("value")
    public String getIRI() {
        return this.strIRI;
    }

    @Override
    public List<Value> createResources(ParsedIRI baseIRI, ValueFactory factory,
                                        RepositoryConnection connection,Project project)
    {
        this.baseIRI = baseIRI;
        this.theFactory = factory;
        this.theConnection = connection;
        this.theProject = project;

        List<Value> listResources = new ArrayList<Value>();
        this.normalizeResource(this.strIRI, listResources);
        if ( listResources.isEmpty() )
            listResources = null;
        return listResources;
    }

	@Override
	protected void writeNode(JsonGenerator writer) throws JsonGenerationException, IOException {
		writer.writeStringField("nodeType", ConstantResourceNode.strNODETYPE);
        writer.writeStringField("value", strIRI);
	}
}
