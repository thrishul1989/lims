package com.todaysoft.lims.testing.primerdesign.model;

import java.util.List;

public class PrimerDesignSubmitRequest
{
    private String id;
    
    private List<PrimerDesignSubmitTaskArgs> tasks;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public List<PrimerDesignSubmitTaskArgs> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<PrimerDesignSubmitTaskArgs> tasks)
    {
        this.tasks = tasks;
    }
}
