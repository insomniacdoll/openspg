package com.antgroup.openspg.cloudext.impl.graphstore.neo4j;

import com.antgroup.openspg.cloudext.interfaces.graphstore.GraphStoreClient;
import com.antgroup.openspg.cloudext.interfaces.graphstore.GraphStoreClientDriver;
import com.antgroup.openspg.common.util.cloudext.CachedCloudExtClientDriver;

public class Neo4jGraphStoreClientDriver extends CachedCloudExtClientDriver<GraphStoreClient> implements GraphStoreClientDriver {
    @Override
    protected GraphStoreClient innerConnect(String connInfo) {
        return null;
    }

    @Override
    public String driverScheme() {
        return "";
    }
}
