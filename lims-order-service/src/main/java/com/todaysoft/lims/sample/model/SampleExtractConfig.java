package com.todaysoft.lims.sample.model;


public class SampleExtractConfig
{
    private Integer id;

    private Sample source;
    
    private Sample target;
    
    private String sortTasks;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Sample getSource()
    {
        return source;
    }
    
    public void setSource(Sample source)
    {
        this.source = source;
    }
    
    public Sample getTarget()
    {
        return target;
    }
    
    public void setTarget(Sample target)
    {
        this.target = target;
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
