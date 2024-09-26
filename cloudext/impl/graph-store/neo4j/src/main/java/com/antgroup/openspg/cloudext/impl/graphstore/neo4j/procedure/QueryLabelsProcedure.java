package com.antgroup.openspg.cloudext.impl.graphstore.neo4j.procedure;

import com.antgroup.openspg.cloudext.impl.graphstore.neo4j.model.TypeEnum;

public class QueryLabelsProcedure extends BaseNeo4jGraphProcedure {

    /**
     * The cypher template for querying vertex labels
     */
    private static final String QUERY_VERTEX_LABELS_CYPHER_TEMPLATE =
            "CALL db.vertexLabels() YIELD label return label as labelName";

    /**
     * The cypher template for querying edge labels
     */
    private static final String QUERY_EDGE_LABELS_CYPHER_TEMPLATE =
            "CALL db.edgeLabels() YIELD label return label as labelName";

    /**
     * The constructor.
     */
    private QueryLabelsProcedure(String cypherTemplate) {
        super(cypherTemplate);
    }

    /**
     * Query labels procedure of data type.
     *
     * @param neo4jGraphDataTypeEnum the data type in Neo4j, either "vertex" or "edge"
     */
    public static QueryLabelsProcedure of(TypeEnum neo4jGraphDataTypeEnum) {
        switch (neo4jGraphDataTypeEnum) {
            case VERTEX:
                return new QueryLabelsProcedure(QUERY_VERTEX_LABELS_CYPHER_TEMPLATE);
            case EDGE:
                return new QueryLabelsProcedure(QUERY_EDGE_LABELS_CYPHER_TEMPLATE);
            default:
                throw new IllegalArgumentException(
                        "unexpected neo4j graph data type enum:" + neo4jGraphDataTypeEnum);
        }
    }

    @Override
    public String toString() {
        return "{\"procedure\":\"QueryLabelsProcedure\", "
                + "\"cypherTemplate\":\""
                + getCypherTemplate()
                + "\"}";
    }
}
