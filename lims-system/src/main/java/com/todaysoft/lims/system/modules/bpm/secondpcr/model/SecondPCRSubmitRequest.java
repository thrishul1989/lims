package com.todaysoft.lims.system.modules.bpm.secondpcr.model;


import java.util.List;

public class SecondPCRSubmitRequest
{
    private String id;

    private List<SecondPCRSubmitTaskArgs> tasks;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }

    public List<SecondPCRSubmitTaskArgs> getTasks() {
        return tasks;
    }

    public void setTasks(List<SecondPCRSubmitTaskArgs> tasks) {
        this.tasks = tasks;
    }
}
