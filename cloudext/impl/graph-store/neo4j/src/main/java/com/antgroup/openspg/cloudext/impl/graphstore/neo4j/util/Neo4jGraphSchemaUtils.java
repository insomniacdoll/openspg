package com.antgroup.openspg.cloudext.impl.graphstore.neo4j.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.antgroup.openspg.cloudext.impl.graphstore.neo4j.convertor.Neo4jGraphSchemaConvertor;
import com.antgroup.openspg.cloudext.impl.graphstore.neo4j.model.TypeEnum;
import com.antgroup.openspg.cloudext.impl.graphstore.neo4j.procedure.GetEdgeSchemaProcedure;
import com.antgroup.openspg.cloudext.impl.graphstore.neo4j.procedure.GetVertexSchemaProcedure;
import com.antgroup.openspg.cloudext.impl.graphstore.neo4j.procedure.QueryLabelsProcedure;
import com.antgroup.openspg.cloudext.impl.graphstore.neo4j.result.GetEdgeSchemaResult;
import com.antgroup.openspg.cloudext.impl.graphstore.neo4j.result.GetVertexSchemaResult;
import com.antgroup.openspg.cloudext.impl.graphstore.neo4j.result.QueryLabelsResult;
import com.antgroup.openspg.cloudext.interfaces.graphstore.model.lpg.schema.EdgeType;
import com.antgroup.openspg.cloudext.interfaces.graphstore.model.lpg.schema.VertexType;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;

import java.util.List;

@Slf4j
public class Neo4jGraphSchemaUtils {

    public static List<VertexType> getVertexTypes(
            Driver client, String graphName, Double timeout) throws Exception {
        List<String> labelNames = getSchemaLabels(TypeEnum.VERTEX, client, graphName, timeout);
        if (CollectionUtils.isEmpty(labelNames)) {
            return Lists.newArrayList();
        }
        List<VertexType> vertexSchemaList = Lists.newArrayList();
        for (String labelName : labelNames) {
            VertexType vertexSchema = getVertexSchemaByLabel(labelName, client, graphName, timeout);
            if (vertexSchema != null) {
                vertexSchemaList.add(vertexSchema);
            }
        }
        return vertexSchemaList;
    }

    public static List<EdgeType> getEdgeTypes(
            Driver client, String graphName, Double timeout) throws Exception {
        List<String> labelNames = getSchemaLabels(TypeEnum.EDGE, client, graphName, timeout);
        if (CollectionUtils.isEmpty(labelNames)) {
            return Lists.newArrayList();
        }
        List<EdgeType> edgeSchemaList = Lists.newArrayList();
        for (String labelName : labelNames) {
            EdgeType edgeSchema = getEdgeSchemaByLabel(labelName, client, graphName, timeout);
            if (edgeSchema != null) {
                edgeSchemaList.add(edgeSchema);
            }
        }
        return edgeSchemaList;
    }

    public static List<String> getSchemaLabels(
            TypeEnum dataTypeEnum, Driver client, String graphName, Double timeout)
            throws Exception {
        String cypher = QueryLabelsProcedure.of(dataTypeEnum).getCypher();
        Session session = client.session();
        Result result = session.run(cypher);
        String labelsJsonStr = result.toString();
        List<QueryLabelsResult> results =
                JSON.parseObject(labelsJsonStr, new TypeReference<List<QueryLabelsResult>>() {
                });
        return Neo4jGraphSchemaConvertor.toLabels(results);
    }

    public static VertexType getVertexSchemaByLabel(
            String labelName, Driver client, String graphName, Double timeout)
            throws Exception {
        String cypher = GetVertexSchemaProcedure.of(labelName).getCypher();
        Session session = client.session();
        Result result = session.run(cypher);
        String vertexSchemaJsonStr = result.toString();
        List<GetVertexSchemaResult> results =
                JSON.parseObject(vertexSchemaJsonStr, new TypeReference<List<GetVertexSchemaResult>>() {
                });
        if (CollectionUtils.isEmpty(results)) {
            return null;
        }
        return Neo4jGraphSchemaConvertor.toVertexType(results.get(0));
    }

    public static EdgeType getEdgeSchemaByLabel(
            String labelName, Driver client, String graphName, Double timeout)
            throws Exception {
        String cypher = GetEdgeSchemaProcedure.of(labelName).getCypher();
        Session session = client.session();
        Result result = session.run(cypher);
        String edgeSchemaJsonStr = result.toString();
        List<GetEdgeSchemaResult> results =
                JSON.parseObject(edgeSchemaJsonStr, new TypeReference<List<GetEdgeSchemaResult>>() {
                });
        if (CollectionUtils.isEmpty(results)) {
            return null;
        }
        return Neo4jGraphSchemaConvertor.toEdgeType(results.get(0));
    }

}
