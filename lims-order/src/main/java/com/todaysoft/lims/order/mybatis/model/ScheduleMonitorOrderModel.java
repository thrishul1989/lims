package com.todaysoft.lims.order.mybatis.model;

import java.util.Date;

public class ScheduleMonitorOrderModel extends BaseOrderModel
{
    private Date plannedFinishDate;
    
    private boolean postponed;
    
    private Integer postponedDays;

    private String projectManager;

    public String getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }

    public Date getPlannedFinishDate()
    {
        return plannedFinishDate;
    }
    
    public void setPlannedFinishDate(Date plannedFinishDate)
    {
        this.plannedFinishDate = plannedFinishDate;
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
}
