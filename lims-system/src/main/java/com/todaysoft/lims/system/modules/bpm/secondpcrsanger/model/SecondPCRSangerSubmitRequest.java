package com.todaysoft.lims.system.modules.bpm.secondpcrsanger.model;


import java.util.List;

public class SecondPCRSangerSubmitRequest
{
    private String id;

    private List<SecondPCRSangerSubmitTaskArgs> tasks;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }

    public List<SecondPCRSangerSubmitTaskArgs> getTasks() {
        return tasks;
    }

    public void setTasks(List<SecondPCRSangerSubmitTaskArgs> tasks) {
        this.tasks = tasks;
    }
}
