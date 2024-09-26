package com.antgroup.openspg.cloudext.impl.graphstore.neo4j;


import com.antgroup.openspg.cloudext.impl.graphstore.neo4j.util.Neo4jGraphSchemaUtils;
import com.antgroup.openspg.cloudext.interfaces.graphstore.BaseLPGGraphStoreClient;
import com.antgroup.openspg.cloudext.interfaces.graphstore.LPGInternalIdGenerator;
import com.antgroup.openspg.cloudext.interfaces.graphstore.LPGTypeNameConvertor;
import com.antgroup.openspg.cloudext.interfaces.graphstore.cmd.BaseLPGRecordQuery;
import com.antgroup.openspg.cloudext.interfaces.graphstore.impl.NoChangedIdGenerator;
import com.antgroup.openspg.cloudext.interfaces.graphstore.model.lpg.record.EdgeRecord;
import com.antgroup.openspg.cloudext.interfaces.graphstore.model.lpg.record.VertexRecord;
import com.antgroup.openspg.cloudext.interfaces.graphstore.model.lpg.record.struct.BaseLPGRecordStruct;
import com.antgroup.openspg.cloudext.interfaces.graphstore.model.lpg.schema.EdgeType;
import com.antgroup.openspg.cloudext.interfaces.graphstore.model.lpg.schema.LPGSchema;
import com.antgroup.openspg.cloudext.interfaces.graphstore.model.lpg.schema.VertexType;
import com.antgroup.openspg.cloudext.interfaces.graphstore.model.lpg.schema.operation.*;
import com.antgroup.openspg.cloudext.interfaces.graphstore.util.TypeNameUtils;
import com.antgroup.openspg.server.api.facade.ApiConstants;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Slf4j
public class Neo4jGraphStoreClient extends BaseLPGGraphStoreClient {

    private final String database;
    private final Double timeout;
    private final Driver client;
    @Getter
    private final LPGInternalIdGenerator internalIdGenerator;
    @Getter
    private final LPGTypeNameConvertor typeNameConvertor;
    @Getter
    private final String connUrl;

    public Neo4jGraphStoreClient(String connUrl, LPGTypeNameConvertor typeNameConvertor) {
        UriComponents uriComponents = UriComponentsBuilder.fromUriString(connUrl).build();
        this.connUrl = connUrl;
        this.database = uriComponents.getQueryParams().getFirst(Neo4jGraphConstants.DATABASE);
        this.timeout = Double.parseDouble(String.valueOf(uriComponents.getQueryParams().getFirst(ApiConstants.TIMEOUT)));
        this.client = initNeo4jGraphClient(uriComponents);
        this.internalIdGenerator = new NoChangedIdGenerator();
        this.typeNameConvertor = typeNameConvertor;
    }

    @Override
    public void close() throws Exception {
        if (client != null) {
            client.close();
        }
    }

    private Driver initNeo4jGraphClient(UriComponents uriComponents) {
        String host = String.format("neo4j://%s:%s", uriComponents.getHost(), uriComponents.getPort());
        String username = uriComponents.getQueryParams().getFirst(ApiConstants.USERNAME);
        String password = uriComponents.getQueryParams().getFirst(ApiConstants.PASSWORD);
        Driver driver;
        try {
            driver = GraphDatabase.driver(host, AuthTokens.basic(username, password));
        } catch (Exception e) {
            throw new RuntimeException("init Neo4j Client failed", e);
        }
        return driver;
    }

    @Override
    public LPGSchema querySchema() {
        List<VertexType> vertexTypes = null;
        List<EdgeType> edgeTypes = null;
        try {
            vertexTypes = Neo4jGraphSchemaUtils.getVertexTypes(client, database, timeout);
            edgeTypes = Neo4jGraphSchemaUtils.getEdgeTypes(client, database, timeout);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        LPGSchema lpgSchema = new LPGSchema(vertexTypes, edgeTypes);
        TypeNameUtils.restoreTypeName(lpgSchema, typeNameConvertor);
        return lpgSchema;
    }

    @Override
    public boolean createVertexType(CreateVertexTypeOperation operation) {
        return false;
    }

    @Override
    public boolean createEdgeType(CreateEdgeTypeOperation operation) {
        return false;
    }

    @Override
    public boolean alterVertexType(AlterVertexTypeOperation operation) {
        return false;
    }

    @Override
    public boolean alterEdgeType(AlterEdgeTypeOperation operation) {
        return false;
    }

    @Override
    public boolean dropVertexType(DropVertexTypeOperation operation) {
        return false;
    }

    @Override
    public boolean dropEdgeType(DropEdgeTypeOperation operation) {
        return false;
    }

    @Override
    public boolean batchTransactionalSchemaOperations(List<BaseLPGSchemaOperation> operations) {
        return false;
    }

    @Override
    public void upsertVertex(String vertexTypeName, List<VertexRecord> vertexRecords) throws Exception {

    }

    @Override
    public void deleteVertex(String vertexTypeName, List<VertexRecord> vertexRecords) throws Exception {

    }

    @Override
    public void upsertEdge(String edgeTypeName, List<EdgeRecord> edgeRecords) throws Exception {

    }

    @Override
    public void deleteEdge(String edgeTypeName, List<EdgeRecord> edgeRecords) throws Exception {

    }

    @Override
    public BaseLPGRecordStruct queryRecord(BaseLPGRecordQuery query) {
        return null;
    }

    @Override
    public String getConnUrl() {
        return "";
    }
}
