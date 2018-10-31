package com.todaysoft.lims.schedule.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_SCHEDULE_PLAN_TASK_DEPENDENCY")
public class OrderPlanTaskDependency extends UuidKeyEntity
{
    private static final long serialVersionUID = -2180639245100390643L;
    
    private String taskId;
    
    private String dependencyTaskId;
    
    @Column(name = "TASK_ID")
    public String getTaskId()
    {
        return taskId;
    }
    
    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }
    
    @Column(name = "DEPENDENCY_TASK_ID")
    public String getDependencyTaskId()
    {
        return dependencyTaskId;
    }
    
    public void setDependencyTaskId(String dependencyTaskId)
    {
        this.dependencyTaskId = dependencyTaskId;
    }
    
}
