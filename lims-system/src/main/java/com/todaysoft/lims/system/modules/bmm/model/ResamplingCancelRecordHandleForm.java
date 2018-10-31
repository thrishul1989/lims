package com.todaysoft.lims.system.modules.bmm.model;

import java.util.List;

public class ResamplingCancelRecordHandleForm
{
    private String id;
    
    private String strategy;
    
    private List<String> riskTestingNodes;
    
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
    
    public List<String> getRiskTestingNodes()
    {
        return riskTestingNodes;
    }
    
    public void setRiskTestingNodes(List<String> riskTestingNodes)
    {
        this.riskTestingNodes = riskTestingNodes;
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
