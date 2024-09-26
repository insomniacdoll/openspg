package com.antgroup.openspg.cloudext.impl.graphstore.neo4j.procedure;

import com.antgroup.openspg.common.util.StringUtils;
import com.google.common.collect.Maps;

import java.lang.reflect.Field;
import java.util.Map;

public abstract class BaseNeo4jGraphProcedure implements Neo4jGraphProcedure {

    /**
     * The cypher template of this procedure
     */
    private final String cypherTemplate;

    protected BaseNeo4jGraphProcedure(String cypherTemplate) {
        this.cypherTemplate = cypherTemplate;
    }

    /**
     * Export parameters of procedure
     *
     * @return A map which describe the parameters of this procedure.
     */
    protected final Map<String, Object> exportParams() {
        Map<String, Object> paramMap = Maps.newHashMap();
        for (Field field : this.getClass().getDeclaredFields()) {
            String fieldName = field.getName();
            field.setAccessible(true);
            try {
                if (field.get(this) == null) {
                    paramMap.put(fieldName, null);
                } else {
                    paramMap.put(fieldName, field.get(this).toString());
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return paramMap;
    }

    /**
     * Get cypher template of this procedure.
     *
     * @return the cypher.
     */
    protected String getCypherTemplate() {
        return this.cypherTemplate;
    }

    @Override
    public String getCypher() {
        return StringUtils.dictFormat(exportParams(), getCypherTemplate());
    }
}
