package com.todaysoft.lims.ngs.model;

import java.math.BigDecimal;
import java.util.List;

import com.google.common.collect.Lists;

public class TaskConfig
{
    
    private String id;
    
    private String semantic;
    
    private String type;
    
    private String name;
    
    private String description;
    
    private String outputSampleId;
    
    private BigDecimal outputVolume;

 
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getSemantic()
    {
        return semantic;
    }
    
    public void setSemantic(String semantic)
    {
        this.semantic = semantic;
    }
    
    public String getType()
    {
        return type;
    }
    
    public void setType(String type)
    {
        this.type = type;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public String getOutputSampleId()
    {
        return outputSampleId;
    }
    
    public void setOutputSampleId(String outputSampleId)
    {
        this.outputSampleId = outputSampleId;
    }
    
    public BigDecimal getOutputVolume()
    {
        return outputVolume;
    }
    
    public void setOutputVolume(BigDecimal outputVolume)
    {
        this.outputVolume = outputVolume;
    }


   
}
