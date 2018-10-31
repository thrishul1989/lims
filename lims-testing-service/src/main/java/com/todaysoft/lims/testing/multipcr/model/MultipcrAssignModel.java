package com.todaysoft.lims.testing.multipcr.model;

import java.util.List;

import com.todaysoft.lims.testing.base.entity.TestingMultipcrTask;

public class MultipcrAssignModel
{
    private String location;
    private List<TestingMultipcrTask> tasks;
    
    public List<TestingMultipcrTask> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<TestingMultipcrTask> tasks)
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
