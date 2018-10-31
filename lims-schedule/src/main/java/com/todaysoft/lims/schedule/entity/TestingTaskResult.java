package com.todaysoft.lims.schedule.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "LIMS_TESTING_TASK_RESULT")
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
    
    @Id
    @Column(name = "TASK_ID")
    public String getTaskId()
    {
        return taskId;
    }
    
    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }
    
    @Column(name = "RESULT")
    public String getResult()
    {
        return result;
    }
    
    public void setResult(String result)
    {
        this.result = result;
    }
    
    @Column(name = "STRATEGY")
    public String getStrategy()
    {
        return strategy;
    }
    
    public void setStrategy(String strategy)
    {
        this.strategy = strategy;
    }
    
    @Column(name = "REMARK")
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    @Column(name = "DETAILS")
    public String getDetails()
    {
        return details;
    }
    
    public void setDetails(String details)
    {
        this.details = details;
    }
}
