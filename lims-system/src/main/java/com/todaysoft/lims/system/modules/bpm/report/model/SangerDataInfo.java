package com.todaysoft.lims.system.modules.bpm.report.model;

import com.google.common.collect.Maps;
import com.todaysoft.lims.system.modules.report.model.DataTemplate;

import java.util.Map;

public class SangerDataInfo
{
    private String combineCode;
    
    private String mutationSource;//突变来源
    
    private String combineType;
    
    private String remark;

    private Map<String,String> map = Maps.newHashMap();

    private DataTemplate dataTemplate;

    public DataTemplate getDataTemplate() {
        return dataTemplate;
    }

    public void setDataTemplate(DataTemplate dataTemplate) {
        this.dataTemplate = dataTemplate;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    public String getCombineType() {
        return combineType;
    }

    public void setCombineType(String combineType) {
        this.combineType = combineType;
    }

    public String getCombineCode()
    {
        return combineCode;
    }
    
    public void setCombineCode(String combineCode)
    {
        this.combineCode = combineCode;
    }
    
    public String getMutationSource()
    {
        return mutationSource;
    }
    
    public void setMutationSource(String mutationSource)
    {
        this.mutationSource = mutationSource;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
}
