package com.todaysoft.lims.system.modules.bpm.rqt.model;

import java.util.List;

public class RQTSubmitRequest
{
    private String id;
    
    private List<RQTSubmitTaskArgs> tasks;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public List<RQTSubmitTaskArgs> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<RQTSubmitTaskArgs> tasks)
    {
        this.tasks = tasks;
    }
}
