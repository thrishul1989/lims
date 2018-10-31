package com.todaysoft.lims.gateway.model;

import java.util.List;

public class SampleExtractConfig
{
    private String id;
    
    private Sample source;
    
    private Sample target;
    
    private String sortTasks;
    
    private List<Task> tasks;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
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
    
    public List<Task> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<Task> tasks)
    {
        this.tasks = tasks;
    }
}
