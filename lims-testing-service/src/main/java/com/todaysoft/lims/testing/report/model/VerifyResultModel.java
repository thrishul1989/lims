package com.todaysoft.lims.testing.report.model;


import com.google.common.collect.Maps;

import java.util.Map;

public class VerifyResultModel
{
    private String combineType;//纯合/杂合

    private String mutationSource;//突变来源

    private String remark;

    private Map<String,String> map = Maps.newHashMap();

    public String getCombineType() {
        return combineType;
    }

    public void setCombineType(String combineType) {
        this.combineType = combineType;
    }

    public String getMutationSource() {
        return mutationSource;
    }

    public void setMutationSource(String mutationSource) {
        this.mutationSource = mutationSource;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }
}
