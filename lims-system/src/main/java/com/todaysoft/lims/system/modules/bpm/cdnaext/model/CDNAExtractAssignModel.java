package com.todaysoft.lims.system.modules.bpm.cdnaext.model;

import java.util.List;

public class CDNAExtractAssignModel
{
    private List<CDNAExtractTask> tasks;
    
    public List<CDNAExtractTask> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<CDNAExtractTask> tasks)
    {
        this.tasks = tasks;
    }
}
