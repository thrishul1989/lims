package com.todaysoft.lims.system.model.vo.order;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


public class TestingTaskResult implements Serializable
{
    public static final String RESULT_SUCCESS = "0";
    
    public static final String RESULT_FAILURE_REPORT = "1";
    
    public static final String RESULT_FAILURE_SOLVE = "2";
    
    private static final long serialVersionUID = 256107762541801455L;
    
    private String taskId;
    
    private String result;
    
    private String remark;
    
    private String strategy;
    
    private String details;
    
    
    public String getTaskId()
    {
        return taskId;
    }
    
    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }
    
    public String getResult()
    {
        return result;
    }
    
    public void setResult(String result)
    {
        this.result = result;
    }
    
    public String getStrategy()
    {
        return strategy;
    }
    
    public void setStrategy(String strategy)
    {
        this.strategy = strategy;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public String getDetails()
    {
        return details;
    }
    
    public void setDetails(String details)
    {
        this.details = details;
    }
}
