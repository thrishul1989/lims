package com.todaysoft.lims.system.modules.bcm.model;

import java.util.List;

import org.springframework.util.CollectionUtils;

public class SamplePretestingConfig
{
    private String receivedSampleId;
    
    private String testingSampleId;
    
    private List<String> pretestingNodes;
    
    public String getNodes()
    {
        if (CollectionUtils.isEmpty(pretestingNodes))
        {
            return "暂无配置";
        }
        
        StringBuffer buf = new StringBuffer(128);
        
        for (String node : pretestingNodes)
        {
            if (buf.length() > 0)
            {
                buf.append(" / ");
            }
            
            buf.append(node);
        }
        
        return buf.toString();
    }
    
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
