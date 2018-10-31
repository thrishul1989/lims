package com.todaysoft.lims.system.model.transation;

import java.util.List;

public class VariableModel
{
    
    private String scheduleIds;
    
    private List<TestingStartRecord> startRecors;
    
    private List<TestingTaskForUpdateRedundant> tasks;
    
    public List<TestingStartRecord> getStartRecors()
    {
        return startRecors;
    }
    
    public void setStartRecors(List<TestingStartRecord> startRecors)
    {
        this.startRecors = startRecors;
    }
    
    public String getScheduleIds()
    {
        return scheduleIds;
    }
    
    public void setScheduleIds(String scheduleIds)
    {
        this.scheduleIds = scheduleIds;
    }
    
    public List<TestingTaskForUpdateRedundant> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<TestingTaskForUpdateRedundant> tasks)
    {
        this.tasks = tasks;
    }
    
}
