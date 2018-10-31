package com.todaysoft.lims.testing.base.model;

import java.math.BigDecimal;

public class TaskInputConfig
{
    
    private String id;
    
    private TaskConfig task;
    
    private String inputSampleId;
    
    private String reagentKitId;
    
    private BigDecimal inputSize;
    
    private BigDecimal inputVolume;
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((inputSampleId == null) ? 0 : inputSampleId.hashCode());
        result = prime * result + ((inputSize == null) ? 0 : inputSize.hashCode());
        result = prime * result + ((inputVolume == null) ? 0 : inputVolume.hashCode());
        result = prime * result + ((reagentKitId == null) ? 0 : reagentKitId.hashCode());
        result = prime * result + ((task == null) ? 0 : task.hashCode());
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
        TaskInputConfig other = (TaskInputConfig)obj;
        if (id == null)
        {
            if (other.id != null)
                return false;
        }
        else if (!id.equals(other.id))
            return false;
        if (inputSampleId == null)
        {
            if (other.inputSampleId != null)
                return false;
        }
        else if (!inputSampleId.equals(other.inputSampleId))
            return false;
        if (inputSize == null)
        {
            if (other.inputSize != null)
                return false;
        }
        else if (!inputSize.equals(other.inputSize))
            return false;
        if (inputVolume == null)
        {
            if (other.inputVolume != null)
                return false;
        }
        else if (!inputVolume.equals(other.inputVolume))
            return false;
        if (reagentKitId == null)
        {
            if (other.reagentKitId != null)
                return false;
        }
        else if (!reagentKitId.equals(other.reagentKitId))
            return false;
        if (task == null)
        {
            if (other.task != null)
                return false;
        }
        else if (!task.equals(other.task))
            return false;
        return true;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public TaskConfig getTask()
    {
        return task;
    }
    
    public void setTask(TaskConfig task)
    {
        this.task = task;
    }
    
    public String getInputSampleId()
    {
        return inputSampleId;
    }
    
    public void setInputSampleId(String inputSampleId)
    {
        this.inputSampleId = inputSampleId;
    }
    
    public String getReagentKitId()
    {
        return reagentKitId;
    }
    
    public void setReagentKitId(String reagentKitId)
    {
        this.reagentKitId = reagentKitId;
    }
    
    public BigDecimal getInputSize()
    {
        return inputSize;
    }
    
    public void setInputSize(BigDecimal inputSize)
    {
        this.inputSize = inputSize;
    }
    
    public BigDecimal getInputVolume()
    {
        return inputVolume;
    }
    
    public void setInputVolume(BigDecimal inputVolume)
    {
        this.inputVolume = inputVolume;
    }
}
