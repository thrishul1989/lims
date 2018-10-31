package com.todaysoft.lims.system.modules.bpm.multipcr.model;

import java.util.List;

public class MultipcrAssignModel
{
    private List<MultiPcrTask> tasks;
    
    public List<MultiPcrTask> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<MultiPcrTask> tasks)
    {
        this.tasks = tasks;
    }
}
