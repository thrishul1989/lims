package com.todaysoft.lims.system.service.adapter.request.sample;

public class SampleExtractConfigRequest
{
    private Integer sourceSampleId;
    
    private Integer targetSampleId;
    
    private String sortTasks;
    
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
    
    public String getSortTasks()
    {
        return sortTasks;
    }
    
    public void setSortTasks(String sortTasks)
    {
        this.sortTasks = sortTasks;
    }
}
