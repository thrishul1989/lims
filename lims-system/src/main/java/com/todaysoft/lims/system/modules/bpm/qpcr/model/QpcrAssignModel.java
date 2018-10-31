package com.todaysoft.lims.system.modules.bpm.qpcr.model;

import java.util.List;

public class QpcrAssignModel
{
    private List<QpcrTask> tasks;
    
    public List<QpcrTask> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<QpcrTask> tasks)
    {
        this.tasks = tasks;
    }
}
