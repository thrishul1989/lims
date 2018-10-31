package com.todaysoft.lims.system.modules.bpm.dnaext.model;

import java.util.List;

public class DNAExtractAssignModel
{
    private List<DNAExtractTask> tasks;
    
    public List<DNAExtractTask> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<DNAExtractTask> tasks)
    {
        this.tasks = tasks;
    }
}
