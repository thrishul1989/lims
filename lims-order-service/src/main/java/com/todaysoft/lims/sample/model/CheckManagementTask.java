package com.todaysoft.lims.sample.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_CHECKMANAGEMENT_TASK")
public class CheckManagementTask extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private CheckManagement checkManagement;// 检测管理
    
    private Integer taskId;// 检测ID
    
    private Integer taskOrder;// 检测顺序
    
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "CHECKMANAGEMENT_ID")
    @JsonIgnore
    public CheckManagement getCheckManagement()
    {
        return checkManagement;
    }
    
    public void setCheckManagement(CheckManagement checkManagement)
    {
        this.checkManagement = checkManagement;
    }
    
    @Column(name = "TASK_ID")
    public Integer getTaskId()
    {
        return taskId;
    }
    
    public void setTaskId(Integer taskId)
    {
        this.taskId = taskId;
    }
    
    @Column(name = "TASK_ORDER")
    public Integer getTaskOrder()
    {
        return taskOrder;
    }
    
    public void setTaskOrder(Integer taskOrder)
    {
        this.taskOrder = taskOrder;
    }
    
}
