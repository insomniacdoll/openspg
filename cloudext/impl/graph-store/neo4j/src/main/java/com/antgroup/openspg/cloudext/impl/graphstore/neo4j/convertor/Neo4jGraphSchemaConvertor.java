package com.antgroup.openspg.cloudext.impl.graphstore.neo4j.convertor;

import com.antgroup.openspg.cloudext.impl.graphstore.neo4j.result.GetEdgeSchemaResult;
import com.antgroup.openspg.cloudext.impl.graphstore.neo4j.result.GetVertexSchemaResult;
import com.antgroup.openspg.cloudext.impl.graphstore.neo4j.result.QueryLabelsResult;
import com.antgroup.openspg.cloudext.interfaces.graphstore.model.lpg.schema.EdgeType;
import com.antgroup.openspg.cloudext.interfaces.graphstore.model.lpg.schema.EdgeTypeName;
import com.antgroup.openspg.cloudext.interfaces.graphstore.model.lpg.schema.VertexType;

import java.util.Arrays;
import java.util.List;

public class Neo4jGraphSchemaConvertor {

    public static List<String> toLabels(List<QueryLabelsResult> labelQueryResult) {
        return Arrays.asList();
    }


    public static VertexType toVertexType(GetVertexSchemaResult result) {
        return null;
    }

    public static EdgeType toEdgeType(GetEdgeSchemaResult result) {
        return null;
    }

}
