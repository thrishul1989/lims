package com.todaysoft.lims.maintain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "LIMS_TESTING_TASK_TECHNICAL_ANALY")
public class TechnicalAnalyTestingTask implements Serializable
{
    private static final long serialVersionUID = -752622240450535264L;
    
    private String taskId;
    
    private String sequencingCode;
    
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
    
    @Column(name = "SEQUENCING_CODE")
    public String getSequencingCode()
    {
        return sequencingCode;
    }
    
    public void setSequencingCode(String sequencingCode)
    {
        this.sequencingCode = sequencingCode;
    }
}
