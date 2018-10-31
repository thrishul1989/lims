package com.todaysoft.lims.product.model.request;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.product.entity.TaskInputConfig;
import com.todaysoft.lims.product.entity.UserGroup;

public class TaskConfigRequest
{
    private String id;
    
    private String name;
    
    private String description;
    
    private String outputSampleId;
    
    private BigDecimal outputVolume;
    
    private UserGroup userGroup;
    
    private List<TaskInputConfig> inputConfigs = new ArrayList<TaskInputConfig>();
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
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
    
    public List<TaskInputConfig> getInputConfigs()
    {
        return inputConfigs;
    }
    
    public void setInputConfigs(List<TaskInputConfig> inputConfigs)
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
