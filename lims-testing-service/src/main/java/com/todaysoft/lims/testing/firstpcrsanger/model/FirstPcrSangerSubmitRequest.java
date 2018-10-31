package com.todaysoft.lims.testing.firstpcrsanger.model;

import java.util.List;

public class FirstPcrSangerSubmitRequest
{
    private String id;
    
    private List<FirstPcrSangerSubmitTaskArgs> tasks;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public List<FirstPcrSangerSubmitTaskArgs> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<FirstPcrSangerSubmitTaskArgs> tasks)
    {
        this.tasks = tasks;
    }
}
