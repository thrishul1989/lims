package com.todaysoft.lims.system.modules.bpm.tecanalys.model;

import java.util.List;

public class TechnicalAnalySubmitVerifyRequest
{
    private String id;
    
    private List<TechnicalAnalySubmitVerifyTaskArgs> tasks;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public List<TechnicalAnalySubmitVerifyTaskArgs> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<TechnicalAnalySubmitVerifyTaskArgs> tasks)
    {
        this.tasks = tasks;
    }
}
