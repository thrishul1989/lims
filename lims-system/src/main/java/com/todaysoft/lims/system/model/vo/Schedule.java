package com.todaysoft.lims.system.model.vo;

import java.util.Date;

public class Schedule
{
    
    public Schedule(String id, String task, Date issuedTime)
    {
        super();
        this.id = id;
        this.task = task;
        this.issuedTime = issuedTime;
    }
    
    public Schedule()
    {
    }
    
    private String id;
    
    private String task;
    
    private Date issuedTime;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getTask()
    {
        return task;
    }
    
    public void setTask(String task)
    {
        this.task = task;
    }
    
    public Date getIssuedTime()
    {
        return issuedTime;
    }
    
    public void setIssuedTime(Date issuedTime)
    {
        this.issuedTime = issuedTime;
    }
    
}
