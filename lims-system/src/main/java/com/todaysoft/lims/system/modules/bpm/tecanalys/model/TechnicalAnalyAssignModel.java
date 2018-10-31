package com.todaysoft.lims.system.modules.bpm.tecanalys.model;

import java.util.List;

public class TechnicalAnalyAssignModel
{
    private List<TechnicalAnalyTask> tasks;
    
    public List<TechnicalAnalyTask> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<TechnicalAnalyTask> tasks)
    {
        this.tasks = tasks;
    }
}
