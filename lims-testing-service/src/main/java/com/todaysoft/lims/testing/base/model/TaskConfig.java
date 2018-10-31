package com.todaysoft.lims.testing.base.model;

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
    
    private UserGroup userGroup;
    
    private List<TaskInputConfig> taskInputConfigList = Lists.newArrayList();
    
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
    
    public List<TaskInputConfig> getTaskInputConfigList()
    {
        return taskInputConfigList;
    }
    
    public void setTaskInputConfigList(List<TaskInputConfig> taskInputConfigList)
    {
        this.taskInputConfigList = taskInputConfigList;
    }
    
    public UserGroup getUserGroup()
    {
        return userGroup;
    }
    
    public void setUserGroup(UserGroup userGroup)
    {
        this.userGroup = userGroup;
    }
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((outputSampleId == null) ? 0 : outputSampleId.hashCode());
        result = prime * result + ((outputVolume == null) ? 0 : outputVolume.hashCode());
        result = prime * result + ((semantic == null) ? 0 : semantic.hashCode());
        result = prime * result + ((taskInputConfigList == null) ? 0 : taskInputConfigList.hashCode());
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TaskConfig other = (TaskConfig)obj;
        if (description == null)
        {
            if (other.description != null)
                return false;
        }
        else if (!description.equals(other.description))
            return false;
        if (id == null)
        {
            if (other.id != null)
                return false;
        }
        else if (!id.equals(other.id))
            return false;
        if (name == null)
        {
            if (other.name != null)
                return false;
        }
        else if (!name.equals(other.name))
            return false;
        if (outputSampleId == null)
        {
            if (other.outputSampleId != null)
                return false;
        }
        else if (!outputSampleId.equals(other.outputSampleId))
            return false;
        if (outputVolume == null)
        {
            if (other.outputVolume != null)
                return false;
        }
        else if (!outputVolume.equals(other.outputVolume))
            return false;
        if (semantic == null)
        {
            if (other.semantic != null)
                return false;
        }
        else if (!semantic.equals(other.semantic))
            return false;
        if (taskInputConfigList == null)
        {
            if (other.taskInputConfigList != null)
                return false;
        }
        else if (!taskInputConfigList.equals(other.taskInputConfigList))
            return false;
        if (type == null)
        {
            if (other.type != null)
                return false;
        }
        else if (!type.equals(other.type))
            return false;
        return true;
    }
    
}
