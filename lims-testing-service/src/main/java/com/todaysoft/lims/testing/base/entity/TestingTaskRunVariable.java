package com.todaysoft.lims.testing.base.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "LIMS_TESTING_TASK_RU_VARIABLE")
public class TestingTaskRunVariable implements Serializable
{
    private static final long serialVersionUID = -3295058467145290943L;
    
    private String testingTaskId;
    
    private String text;
    
//    private String runVariables;
    
    @Id
    @Column(name = "TASK_ID")
    public String getTestingTaskId()
    {
        return testingTaskId;
    }
    
    public void setTestingTaskId(String testingTaskId)
    {
        this.testingTaskId = testingTaskId;
    }
    
    @Column(name = "TEXT")
    public String getText()
    {
        return text;
    }
    
    public void setText(String text)
    {
        this.text = text;
    }
    
//    @Column(name = "RUN_VARIABLES")
//    public String getRunVariables()
//    {
//        return runVariables;
//    }
//
//    public void setRunVariables(String runVariables)
//    {
//        this.runVariables = runVariables;
//    }
}
