package com.todaysoft.lims.testing.mlpatest.model;

import java.util.List;

public class MlpaTestSubmitRequest
{
    private String id;
    
    private List<MlpaTestTask> tasks;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }

    public List<MlpaTestTask> getTasks() {
        return tasks;
    }

    public void setTasks(List<MlpaTestTask> tasks) {
        this.tasks = tasks;
    }
}
