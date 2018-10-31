package com.todaysoft.lims.system.model.vo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.system.modules.bcm.model.MetadataSample;
import com.todaysoft.lims.system.modules.smm.model.UserGroup;

public class Task
{
    private String id;
    
    private String semantic;
    
    private String type;
    
    private String name;
    
    private String description;
    
    private String outputSampleId;
    
    private BigDecimal outputVolume;
    
    private List<TaskInput> inputConfigs = new ArrayList<TaskInput>();
    
    private MetadataSample sample;
    
    private UserGroup userGroup;
    
    private Integer status;
    
    public Integer getStatus()
    {
        return status;
    }
    
    public void setStatus(Integer status)
    {
        this.status = status;
    }
    
    public MetadataSample getSample()
    {
        return sample;
    }
    
    public void setSample(MetadataSample sample)
    {
        this.sample = sample;
    }
    
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
    
    public List<TaskInput> getInputConfigs()
    {
        return inputConfigs;
    }
    
    public void setInputConfigs(List<TaskInput> inputConfigs)
    {
        this.inputConfigs = inputConfigs;
    }
    
    public UserGroup getUserGroup()
    {
        return userGroup;
    }
    
    public void setUserGroup(UserGroup userGroup)
    {
        this.userGroup = userGroup;
    }
}
