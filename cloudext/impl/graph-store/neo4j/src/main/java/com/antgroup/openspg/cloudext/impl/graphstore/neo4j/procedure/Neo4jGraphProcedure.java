package com.antgroup.openspg.cloudext.impl.graphstore.neo4j.procedure;

/** Neo4jGraph procedure. */
public interface Neo4jGraphProcedure {

    /**
     * Get cypher of this procedure
     *
     * @return
     */
    String getCypher() throws Exception;

}
