package com.todaysoft.lims.order.mybatis.parameter;

import java.util.Date;

public class ScheduleMonitorOrderSearcher extends BaseOrderSearcher
{
    private String sampleCode;
    
    private String postponedNode;

    private String projectManager;
    
    private Date plannedFinishStartDate;
    
    private Date plannedFinishEndDate;
    
    private Integer priorityStatus;

    public String getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }

    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public String getPostponedNode()
    {
        return postponedNode;
    }
    
    public void setPostponedNode(String postponedNode)
    {
        this.postponedNode = postponedNode;
    }
    
    public Date getPlannedFinishStartDate()
    {
        return plannedFinishStartDate;
    }
    
    public void setPlannedFinishStartDate(Date plannedFinishStartDate)
    {
        this.plannedFinishStartDate = plannedFinishStartDate;
    }
    
    public Date getPlannedFinishEndDate()
    {
        return plannedFinishEndDate;
    }
    
    public void setPlannedFinishEndDate(Date plannedFinishEndDate)
    {
        this.plannedFinishEndDate = plannedFinishEndDate;
    }

    public Integer getPriorityStatus()
    {
        return priorityStatus;
    }

    public void setPriorityStatus(Integer priorityStatus)
    {
        this.priorityStatus = priorityStatus;
    }
    
}
