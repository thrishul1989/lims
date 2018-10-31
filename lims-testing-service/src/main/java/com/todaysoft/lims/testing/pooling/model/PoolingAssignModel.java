package com.todaysoft.lims.testing.pooling.model;

import java.util.List;

public class PoolingAssignModel
{
    private String id;
    
    private String code;
    
    private List<PoolingAssignTask> tasks;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public List<PoolingAssignTask> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<PoolingAssignTask> tasks)
    {
        this.tasks = tasks;
    }
}
