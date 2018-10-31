package com.todaysoft.lims.testing.pcrngstest.model;

import java.util.List;

public class PcrNgsTestSubmitRequest
{
    private String id;
    
    private List<PcrNgsTestSubmitTaskArgs> tasks;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public List<PcrNgsTestSubmitTaskArgs> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<PcrNgsTestSubmitTaskArgs> tasks)
    {
        this.tasks = tasks;
    }
}
