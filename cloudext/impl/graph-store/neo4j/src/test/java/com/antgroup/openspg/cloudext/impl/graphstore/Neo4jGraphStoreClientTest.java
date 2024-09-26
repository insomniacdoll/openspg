package com.antgroup.openspg.cloudext.impl.graphstore;

import com.antgroup.openspg.cloudext.impl.graphstore.neo4j.Neo4jGraphStoreClient;
import com.antgroup.openspg.cloudext.interfaces.graphstore.impl.DefaultLPGTypeNameConvertor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class Neo4jGraphStoreClientTest {

    @Test
    public void testInitNeo4jGraphClient() {
        log.info("Hello, test");
        String connUrl = "neo4j://192.168.64.8:7687?database=default&timeout=50000&username=neo4j&password=neo4jneo4j";
        Neo4jGraphStoreClient client = new Neo4jGraphStoreClient(connUrl, new DefaultLPGTypeNameConvertor());
        client.getConnUrl();
    }

}
