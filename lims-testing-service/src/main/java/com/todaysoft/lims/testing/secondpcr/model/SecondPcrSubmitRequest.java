package com.todaysoft.lims.testing.secondpcr.model;

import java.util.List;

public class SecondPcrSubmitRequest
{
    private String id;
    
    private List<SecondPcrSubmitTaskArgs> tasks;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public List<SecondPcrSubmitTaskArgs> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<SecondPcrSubmitTaskArgs> tasks)
    {
        this.tasks = tasks;
    }
}
