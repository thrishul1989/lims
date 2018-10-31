package com.todaysoft.lims.testing.qpcr.model;

import java.util.List;

import com.todaysoft.lims.testing.dnaqc.model.DNAQcTask;

public class QpcrTestAssignModel
{
private List<QpcrTestTask> tasks;

public List<QpcrTestTask> getTasks()
{
    return tasks;
}

public void setTasks(List<QpcrTestTask> tasks)
{
    this.tasks = tasks;
}
    
   
}
