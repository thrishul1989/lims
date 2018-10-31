package com.todaysoft.lims.testing.longpcr.model;

import java.util.List;

import com.todaysoft.lims.testing.base.entity.TestingLongpcrTask;

public class LongpcrAssignModel
{
    private String location;
    private List<TestingLongpcrTask> tasks;
    
    public List<TestingLongpcrTask> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<TestingLongpcrTask> tasks)
    {
        this.tasks = tasks;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }
    
}
