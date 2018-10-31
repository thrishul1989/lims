package com.todaysoft.lims.product.model;

import java.util.List;

public class SamplePretestingConfigDetailModel
{
    private String receivedSampleId;
    
    private String testingSampleId;
    
    private List<String> pretestingNodes;
    
    public String getReceivedSampleId()
    {
        return receivedSampleId;
    }
    
    public void setReceivedSampleId(String receivedSampleId)
    {
        this.receivedSampleId = receivedSampleId;
    }
    
    public String getTestingSampleId()
    {
        return testingSampleId;
    }
    
    public void setTestingSampleId(String testingSampleId)
    {
        this.testingSampleId = testingSampleId;
    }
    
    public List<String> getPretestingNodes()
    {
        return pretestingNodes;
    }
    
    public void setPretestingNodes(List<String> pretestingNodes)
    {
        this.pretestingNodes = pretestingNodes;
    }
}
