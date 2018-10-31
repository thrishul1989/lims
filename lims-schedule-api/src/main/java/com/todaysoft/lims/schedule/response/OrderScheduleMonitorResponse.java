package com.todaysoft.lims.schedule.response;

import java.util.Date;
import java.util.List;

public class OrderScheduleMonitorResponse
{
    private String id;
    
    private String orderCode;
    
    private Integer status;
    
    private Date submitTime;
    
    private Integer plannedDuration;
    
    private Date plannedFinishDate;
    
    private Date actualFinishDate;
    
    private List<OrderScheduleMonitorPlanTask> tasks;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getOrderCode()
    {
        return orderCode;
    }
    
    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }
    
    public Date getSubmitTime()
    {
        return submitTime;
    }
    
    public void setSubmitTime(Date submitTime)
    {
        this.submitTime = submitTime;
    }
    
    public Integer getPlannedDuration()
    {
        return plannedDuration;
    }
    
    public void setPlannedDuration(Integer plannedDuration)
    {
        this.plannedDuration = plannedDuration;
    }
    
    public Date getPlannedFinishDate()
    {
        return plannedFinishDate;
    }
    
    public void setPlannedFinishDate(Date plannedFinishDate)
    {
        this.plannedFinishDate = plannedFinishDate;
    }
    
    public Date getActualFinishDate()
    {
        return actualFinishDate;
    }
    
    public void setActualFinishDate(Date actualFinishDate)
    {
        this.actualFinishDate = actualFinishDate;
    }
    
    public List<OrderScheduleMonitorPlanTask> getTasks()
    {
        return tasks;
    }
    
    public void setTasks(List<OrderScheduleMonitorPlanTask> tasks)
    {
        this.tasks = tasks;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }
}
