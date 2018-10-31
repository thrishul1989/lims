package com.todaysoft.lims.system.modules.bpm.longpcr.model;

import java.util.List;

public class LongpcrAssignModel
{
    private List<LongPcrTask> tasks;
    
    public List<LongPcrTask> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<LongPcrTask> tasks)
    {
        this.tasks = tasks;
    }
}
