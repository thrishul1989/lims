package com.todaysoft.lims.system.service.adapter.request;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.system.model.vo.TaskInput;
import com.todaysoft.lims.system.modules.smm.model.UserGroup;

public class TaskConfigRequest
{
    private String id;
    
    private String name;
    
    private String description;
    
    private String outputSampleId;
    
    private BigDecimal outputVolume;
    
    private String inputContent;
    
    private UserGroup userGroup;
    
    private List<TaskInput> inputConfigs = new ArrayList<TaskInput>();
    
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
    
    public String getInputContent()
    {
        return inputContent;
    }
    
    public void setInputContent(String inputContent)
    {
        this.inputContent = inputContent;
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
