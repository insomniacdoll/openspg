package com.antgroup.openspg.cloudext.impl.graphstore.neo4j.procedure;

public class GetEdgeSchemaProcedure extends BaseNeo4jGraphProcedure {

    /**
     * The cypher template
     */
    private static final String GET_EDGE_SCHEMA_CYPHER_TEMPLATE =
            "CALL db.getEdgeSchema('${labelName}')";

    /**
     * Name of the label
     */
    private final String labelName;

    /**
     * The constructor
     */
    private GetEdgeSchemaProcedure(String cypherTemplate, String labelName) {
        super(cypherTemplate);
        this.labelName = labelName;
    }

    /**
     * Get edge schema procedure of label name.
     */
    public static GetEdgeSchemaProcedure of(String labelName) {
        return new GetEdgeSchemaProcedure(GET_EDGE_SCHEMA_CYPHER_TEMPLATE, labelName);
    }

    @Override
    public String toString() {
        return "{\"procedure\":\"GetEdgeSchemaProcedure\", "
                + "\"labelName\":\""
                + labelName
                + "\", "
                + "\"cypherTemplate\":\""
                + getCypherTemplate()
                + "\"}";
    }
}
