package com.todaysoft.lims.schedule.response;

import java.util.Date;

public class OrderScheduleMonitorPlanTask
{
    private String id;
    
    private String name;
    
    private Date plannedStartDate;
    
    private Date plannedFinishDate;
    
    private Date actualStartDate;
    
    private Date actualFinishDate;
    
    private boolean actived;
    
    private boolean started;
    
    private boolean finished;
    
    private boolean canceled;
    
    private boolean postponed;
    
    private Integer postponedDays;
    
    private boolean grouped;
    
    private Integer abnormalCount;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public Date getPlannedStartDate()
    {
        return plannedStartDate;
    }
    
    public void setPlannedStartDate(Date plannedStartDate)
    {
        this.plannedStartDate = plannedStartDate;
    }
    
    public Date getPlannedFinishDate()
    {
        return plannedFinishDate;
    }
    
    public void setPlannedFinishDate(Date plannedFinishDate)
    {
        this.plannedFinishDate = plannedFinishDate;
    }
    
    public Date getActualStartDate()
    {
        return actualStartDate;
    }
    
    public void setActualStartDate(Date actualStartDate)
    {
        this.actualStartDate = actualStartDate;
    }
    
    public Date getActualFinishDate()
    {
        return actualFinishDate;
    }
    
    public void setActualFinishDate(Date actualFinishDate)
    {
        this.actualFinishDate = actualFinishDate;
    }
    
    public boolean isActived()
    {
        return actived;
    }
    
    public void setActived(boolean actived)
    {
        this.actived = actived;
    }
    
    public boolean isStarted()
    {
        return started;
    }
    
    public void setStarted(boolean started)
    {
        this.started = started;
    }
    
    public boolean isFinished()
    {
        return finished;
    }
    
    public void setFinished(boolean finished)
    {
        this.finished = finished;
    }
    
    public boolean isCanceled()
    {
        return canceled;
    }
    
    public void setCanceled(boolean canceled)
    {
        this.canceled = canceled;
    }
    
    public boolean isPostponed()
    {
        return postponed;
    }
    
    public void setPostponed(boolean postponed)
    {
        this.postponed = postponed;
    }
    
    public Integer getPostponedDays()
    {
        return postponedDays;
    }
    
    public void setPostponedDays(Integer postponedDays)
    {
        this.postponedDays = postponedDays;
    }
    
    public boolean isGrouped()
    {
        return grouped;
    }
    
    public void setGrouped(boolean grouped)
    {
        this.grouped = grouped;
    }

    public Integer getAbnormalCount()
    {
        return abnormalCount;
    }

    public void setAbnormalCount(Integer abnormalCount)
    {
        this.abnormalCount = abnormalCount;
    }
}
