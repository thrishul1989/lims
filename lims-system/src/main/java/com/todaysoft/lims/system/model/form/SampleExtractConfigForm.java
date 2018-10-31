package com.todaysoft.lims.system.model.form;

import java.util.List;

public class SampleExtractConfigForm
{
    private Integer sourceSampleId;
    
    private Integer targetSampleId;
    
    private List<String> tasks;
    
    public Integer getSourceSampleId()
    {
        return sourceSampleId;
    }
    
    public void setSourceSampleId(Integer sourceSampleId)
    {
        this.sourceSampleId = sourceSampleId;
    }
    
    public Integer getTargetSampleId()
    {
        return targetSampleId;
    }
    
    public void setTargetSampleId(Integer targetSampleId)
    {
        this.targetSampleId = targetSampleId;
    }
    
    public List<String> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<String> tasks)
    {
        this.tasks = tasks;
    }
}
