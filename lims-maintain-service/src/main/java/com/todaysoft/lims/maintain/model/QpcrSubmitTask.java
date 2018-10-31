package com.todaysoft.lims.maintain.model;

import com.google.common.collect.Maps;

import java.util.Map;

public class QpcrSubmitTask
{
    private String id;
    
    private Integer result;
    
    private String dispose;
    
    private String combineType;
    
    private String mutationSource;
    
    private String remark;
    
    private String combineCode;
    
    private String conclusion;
    
    private String negaOrPositive;

    private Map<String,String> map = Maps.newHashMap();//其他多余字段不涉及代码的放map里面
    
    public String getNegaOrPositive()
    {
        return negaOrPositive;
    }
    
    public void setNegaOrPositive(String negaOrPositive)
    {
        this.negaOrPositive = negaOrPositive;
    }
    
    public String getConclusion()
    {
        return conclusion;
    }
    
    public void setConclusion(String conclusion)
    {
        this.conclusion = conclusion;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public Integer getResult()
    {
        return result;
    }
    
    public void setResult(Integer result)
    {
        this.result = result;
    }
    
    public String getDispose()
    {
        return dispose;
    }
    
    public void setDispose(String dispose)
    {
        this.dispose = dispose;
    }
    
    public String getCombineType()
    {
        return combineType;
    }
    
    public void setCombineType(String combineType)
    {
        this.combineType = combineType;
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
    
    public String getCombineCode()
    {
        return combineCode;
    }
    
    public void setCombineCode(String combineCode)
    {
        this.combineCode = combineCode;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }
}
