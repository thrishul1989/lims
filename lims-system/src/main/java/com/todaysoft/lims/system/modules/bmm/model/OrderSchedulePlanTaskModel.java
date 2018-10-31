package com.todaysoft.lims.system.modules.bmm.model;

public class OrderSchedulePlanTaskModel
{
    private String id;
    
    private String name;
    
    private String plannedStartDate;
    
    private String plannedFinishDate;
    
    private String actualStartDate;
    
    private String actualFinishDate;
    
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
    
    public String getPlannedStartDate()
    {
        return plannedStartDate;
    }
    
    public void setPlannedStartDate(String plannedStartDate)
    {
        this.plannedStartDate = plannedStartDate;
    }
    
    public String getPlannedFinishDate()
    {
        return plannedFinishDate;
    }
    
    public void setPlannedFinishDate(String plannedFinishDate)
    {
        this.plannedFinishDate = plannedFinishDate;
    }
    
    public String getActualStartDate()
    {
        return actualStartDate;
    }
    
    public void setActualStartDate(String actualStartDate)
    {
        this.actualStartDate = actualStartDate;
    }
    
    public String getActualFinishDate()
    {
        return actualFinishDate;
    }
    
    public void setActualFinishDate(String actualFinishDate)
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
