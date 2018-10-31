package com.todaysoft.lims.system.model.vo;

import java.math.BigDecimal;

public class TaskInputConfig
{
    private String id;
    
    private TaskConfig task;
    
    private String inputSampleId;
    
    private String reagentKitId;
    
    private BigDecimal inputSize;
    
    private BigDecimal inputVolume;
    
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
