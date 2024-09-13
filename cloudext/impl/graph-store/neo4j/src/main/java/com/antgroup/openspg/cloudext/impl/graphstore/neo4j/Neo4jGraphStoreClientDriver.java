package com.antgroup.openspg.cloudext.impl.graphstore.neo4j;

import com.antgroup.openspg.cloudext.interfaces.graphstore.GraphStoreClient;
import com.antgroup.openspg.cloudext.interfaces.graphstore.GraphStoreClientDriver;
import com.antgroup.openspg.cloudext.interfaces.graphstore.GraphStoreClientDriverManager;
import com.antgroup.openspg.cloudext.interfaces.graphstore.impl.DefaultLPGTypeNameConvertor;
import com.antgroup.openspg.common.util.cloudext.CachedCloudExtClientDriver;

public class Neo4jGraphStoreClientDriver extends CachedCloudExtClientDriver<GraphStoreClient> implements GraphStoreClientDriver {

    static {
        GraphStoreClientDriverManager.registerDriver(new Neo4jGraphStoreClientDriver());
    }

    @Override
    protected GraphStoreClient innerConnect(String connInfo) {
        return new Neo4jGraphStoreClient(connInfo, new DefaultLPGTypeNameConvertor());
    }

    @Override
    public String driverScheme() {
        return "neo4j";
    }
}
