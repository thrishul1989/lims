package com.todaysoft.lims.system.modules.bpm.cycleConfig.model;

public class ScheduleTestingNodeConfigModel
{
    private String configId;
    
    private String nodeSemantic;
    
    private String nodeName;
    
    private int threshold;

    public String getConfigId()
    {
        return configId;
    }

    public void setConfigId(String configId)
    {
        this.configId = configId;
    }

    public String getNodeSemantic()
    {
        return nodeSemantic;
    }

    public void setNodeSemantic(String nodeSemantic)
    {
        this.nodeSemantic = nodeSemantic;
    }

    public String getNodeName()
    {
        return nodeName;
    }

    public void setNodeName(String nodeName)
    {
        this.nodeName = nodeName;
    }

    public int getThreshold()
    {
        return threshold;
    }

    public void setThreshold(int threshold)
    {
        this.threshold = threshold;
    }
    
    
}
