package com.todaysoft.lims.testing.dpcrsanger.model;

import com.google.common.collect.Maps;

import java.util.Map;

public class DataPcrSangerSubmitTaskArgs
{
    private String id;
    
    private Integer result;
    
    private String dispose;
    
    private String remark;
    
    private String combineType; //纯合/杂合
    
    private String mutationSource; //突变来源
    
    private String pcrTestCode;//pcr试验编号
    
    private String combineCode;//合并号

    private String primerId;

    private Integer runningStatus;

    private Map<String,String> map = Maps.newHashMap();//其他多余字段不涉及代码的放map里面

    public String getPrimerId() {
        return primerId;
    }

    public void setPrimerId(String primerId) {
        this.primerId = primerId;
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
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
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
    
    public String getPcrTestCode()
    {
        return pcrTestCode;
    }
    
    public void setPcrTestCode(String pcrTestCode)
    {
        this.pcrTestCode = pcrTestCode;
    }
    
    public String getCombineCode()
    {
        return combineCode;
    }
    
    public void setCombineCode(String combineCode)
    {
        this.combineCode = combineCode;
    }

    public Integer getRunningStatus() {
        return runningStatus;
    }

    public void setRunningStatus(Integer runningStatus) {
        this.runningStatus = runningStatus;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }
}
