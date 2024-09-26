package com.antgroup.openspg.cloudext.impl.graphstore.neo4j.model;

import com.alibaba.fastjson.annotation.JSONField;

/** Data type enum. */
public enum TypeEnum {

    /** Vertex */
    @JSONField(name = "VERTEX")
    VERTEX,

    /** Edge */
    @JSONField(name = "EDGE")
    EDGE;
}