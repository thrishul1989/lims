package com.todaysoft.lims.testing.base.model;

import com.todaysoft.lims.testing.base.entity.TestingTask;

public class TaskSubmitModel
{
    private TestingTask entity;
    
    private boolean success;
    
    private String failureRemark;
    
    private String failureStrategy;
    
    private Object details;
    
    public TestingTask getEntity()
    {
        return entity;
    }
    
    public void setEntity(TestingTask entity)
    {
        this.entity = entity;
    }
    
    public boolean isSuccess()
    {
        return success;
    }
    
    public void setSuccess(boolean success)
    {
        this.success = success;
    }
    
    public String getFailureRemark()
    {
        return failureRemark;
    }
    
    public void setFailureRemark(String failureRemark)
    {
        this.failureRemark = failureRemark;
    }
    
    public String getFailureStrategy()
    {
        return failureStrategy;
    }
    
    public void setFailureStrategy(String failureStrategy)
    {
        this.failureStrategy = failureStrategy;
    }
    
    public Object getDetails()
    {
        return details;
    }
    
    public void setDetails(Object details)
    {
        this.details = details;
    }
}
