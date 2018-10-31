package com.todaysoft.lims.testing.secondpcrsanger.model;

import java.util.List;

public class SecondPcrSangerSubmitRequest
{
    private String id;
    
    private List<SecondPcrSangerSubmitTaskArgs> tasks;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public List<SecondPcrSangerSubmitTaskArgs> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<SecondPcrSangerSubmitTaskArgs> tasks)
    {
        this.tasks = tasks;
    }
}
