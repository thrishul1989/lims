package com.todaysoft.lims.schedule.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "LIMS_ORDER_SCHEDULE_MONITOR")
public class OrderScheduleMonitor implements Serializable
{
    private static final long serialVersionUID = 2477661595171690604L;
    
    private String orderId;
    
    private Integer plannedDuration;
    
    private Date plannedFinishDate;
    
    private Date actualFinishDate;
    
    private boolean postponed;
    
    private Integer postponedDays;
    
    @Id
    @Column(name = "ORDER_ID")
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
    
    @Column(name = "PLANNED_DURATION")
    public Integer getPlannedDuration()
    {
        return plannedDuration;
    }
    
    public void setPlannedDuration(Integer plannedDuration)
    {
        this.plannedDuration = plannedDuration;
    }
    
    @Column(name = "PLANNED_FINISH_DATE")
    @Temporal(TemporalType.DATE)
    public Date getPlannedFinishDate()
    {
        return plannedFinishDate;
    }
    
    public void setPlannedFinishDate(Date plannedFinishDate)
    {
        this.plannedFinishDate = plannedFinishDate;
    }
    
    @Column(name = "ACTUAL_FINISH_DATE")
    @Temporal(TemporalType.DATE)
    public Date getActualFinishDate()
    {
        return actualFinishDate;
    }
    
    public void setActualFinishDate(Date actualFinishDate)
    {
        this.actualFinishDate = actualFinishDate;
    }
    
    @Column(name = "POSTPONED")
    public boolean isPostponed()
    {
        return postponed;
    }
    
    public void setPostponed(boolean postponed)
    {
        this.postponed = postponed;
    }
    
    @Column(name = "POSTPONED_DAYS")
    public Integer getPostponedDays()
    {
        return postponedDays;
    }
    
    public void setPostponedDays(Integer postponedDays)
    {
        this.postponedDays = postponedDays;
    }
}
