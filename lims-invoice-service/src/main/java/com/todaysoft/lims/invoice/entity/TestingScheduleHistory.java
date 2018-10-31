package com.todaysoft.lims.invoice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_TESTING_SCHEDULE_HISTORY")
public class TestingScheduleHistory extends UuidKeyEntity
{
    private static final long serialVersionUID = -968558231755149297L;
    
    private String scheduleId;
    
    private String taskId;
    
    @Column(name = "SCHEDULE_ID")
    public String getScheduleId()
    {
        return scheduleId;
    }
    
    public void setScheduleId(String scheduleId)
    {
        this.scheduleId = scheduleId;
    }
    
    @Column(name = "TASK_ID")
    public String getTaskId()
    {
        return taskId;
    }
    
    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }
}
