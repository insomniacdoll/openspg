package com.antgroup.openspg.cloudext.impl.graphstore.neo4j.procedure;

/**
 * Get vertex schema procedure.
 */
public class GetVertexSchemaProcedure extends BaseNeo4jGraphProcedure {

    /**
     * The cypher template
     */
    static final String GET_VERTEX_SCHEMA_CYPHER_TEMPLATE = "CALL db.getVertexSchema('${labelName}')";

    /**
     * Name of the label
     */
    private final String labelName;

    /**
     * The constructor
     */
    private GetVertexSchemaProcedure(String cypherTemplate, String labelName) {
        super(cypherTemplate);
        this.labelName = labelName;
    }

    /**
     * Get edge schema procedure of label name.
     */
    public static GetVertexSchemaProcedure of(String labelName) {
        return new GetVertexSchemaProcedure(GET_VERTEX_SCHEMA_CYPHER_TEMPLATE, labelName);
    }

    @Override
    public String toString() {
        return "{\"procedure\":\"GetVertexSchemaProcedure\", "
                + "\"labelName\":\""
                + labelName
                + "\", "
                + "\"cypherTemplate\":\""
                + getCypherTemplate()
                + "\"}";
    }

}
