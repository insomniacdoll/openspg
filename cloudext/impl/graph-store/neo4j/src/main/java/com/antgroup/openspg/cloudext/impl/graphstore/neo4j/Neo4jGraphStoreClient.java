package com.antgroup.openspg.cloudext.impl.graphstore.neo4j;


import com.antgroup.openspg.cloudext.interfaces.graphstore.BaseLPGGraphStoreClient;
import com.antgroup.openspg.cloudext.interfaces.graphstore.cmd.BaseLPGRecordQuery;
import com.antgroup.openspg.cloudext.interfaces.graphstore.model.lpg.record.EdgeRecord;
import com.antgroup.openspg.cloudext.interfaces.graphstore.model.lpg.record.VertexRecord;
import com.antgroup.openspg.cloudext.interfaces.graphstore.model.lpg.record.struct.BaseLPGRecordStruct;
import com.antgroup.openspg.cloudext.interfaces.graphstore.model.lpg.schema.LPGSchema;
import com.antgroup.openspg.cloudext.interfaces.graphstore.model.lpg.schema.operation.*;

import java.util.List;

public class Neo4jGraphStoreClient extends BaseLPGGraphStoreClient {

    @Override
    public void close() throws Exception {

    }

    @Override
    public LPGSchema querySchema() {
        return null;
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
