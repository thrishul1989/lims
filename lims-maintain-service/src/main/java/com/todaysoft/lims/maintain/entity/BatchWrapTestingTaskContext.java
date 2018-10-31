package com.todaysoft.lims.maintain.entity;

import com.google.common.collect.Maps;
import com.todaysoft.lims.utils.StringUtils;

import java.util.Map;

public class BatchWrapTestingTaskContext
{

    private Map<String, String> relations = Maps.newHashMap();
    
    public String getRelation(String analRecordId)
    {
        String relationResult = relations.get(analRecordId);

        return StringUtils.isEmpty(relationResult) ? "" : relationResult;
    }

    public Map<String, String> getRelations() {
        return relations;
    }

    public void setRelations(Map<String, String> relations) {
        this.relations = relations;
    }
}
