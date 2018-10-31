package com.todaysoft.lims.technical.model;

import java.util.List;

import com.todaysoft.lims.technical.mybatis.entity.TestingTask;

public class VariableModel
{
    
    private String scheduleIds;
    
    private List<TestingStartRecord> startRecors;
    
    private List<TestingTask> tasks;
    
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
    
    public List<TestingTask> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<TestingTask> tasks)
    {
        this.tasks = tasks;
    }
    
}
