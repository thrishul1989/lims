package com.todaysoft.lims.system.model.vo.order;

import java.util.Date;

public class TestingNode
{
    private String name;
    
    private String type;
    
    private String outputSampleType;
    
    private Date updateTime;
    
    public Date getUpdateTime()
    {
        return updateTime;
    }
    
    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }
    
    public String getType()
    {
        return type;
    }
    
    public void setType(String type)
    {
        this.type = type;
    }
    
    public String getOutputSampleType()
    {
        return outputSampleType;
    }
    
    public void setOutputSampleType(String outputSampleType)
    {
        this.outputSampleType = outputSampleType;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
}
