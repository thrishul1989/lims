package com.todaysoft.lims.testing.dt.model;

import java.util.List;

public class DTSubmitRequest
{
    private String id;
    
    private List<DTTask> tasks;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }

    public List<DTTask> getTasks() {
        return tasks;
    }

    public void setTasks(List<DTTask> tasks) {
        this.tasks = tasks;
    }
}
