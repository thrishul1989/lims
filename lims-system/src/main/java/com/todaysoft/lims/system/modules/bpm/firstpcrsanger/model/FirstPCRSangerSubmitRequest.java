package com.todaysoft.lims.system.modules.bpm.firstpcrsanger.model;

import java.util.List;

public class FirstPCRSangerSubmitRequest
{
    private String id;
    
    private List<FirstPCRSangerSubmitTaskArgs> tasks;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public List<FirstPCRSangerSubmitTaskArgs> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<FirstPCRSangerSubmitTaskArgs> tasks)
    {
        this.tasks = tasks;
    }
}
