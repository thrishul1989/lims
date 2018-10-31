package com.todaysoft.lims.testing.resampling.model;

public class ResamplingCancelRecordHandleRequest
{
    private String id;
    
    private String strategy;
    
    private String riskTestingNode;
    
    private String remark;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getStrategy()
    {
        return strategy;
    }
    
    public void setStrategy(String strategy)
    {
        this.strategy = strategy;
    }
    
    public String getRiskTestingNode()
    {
        return riskTestingNode;
    }
    
    public void setRiskTestingNode(String riskTestingNode)
    {
        this.riskTestingNode = riskTestingNode;
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
