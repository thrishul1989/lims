package com.todaysoft.lims.order.response;

import java.util.Date;

public class ScheduleMonitorOrder extends BaseOrder
{
    private Date plannedFinishDate;
    
    private boolean postponed;
    
    private Integer postponedDays;

    private String prjManagerName;

    public String getPrjManagerName() {
        return prjManagerName;
    }

    public void setPrjManagerName(String prjManagerName) {
        this.prjManagerName = prjManagerName;
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
