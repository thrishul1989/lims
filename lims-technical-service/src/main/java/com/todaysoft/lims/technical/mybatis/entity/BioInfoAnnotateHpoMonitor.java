package com.todaysoft.lims.technical.mybatis.entity;

import java.util.Date;

public class BioInfoAnnotateHpoMonitor
{
    private String annotateId;
    
    private String monitorTaskId;
    
    private Boolean status;
    
    private Date createTime;
    
    private Date endTime;
    
    private String sampleTestId;
    
    public String getAnnotateId()
    {
        return annotateId;
    }
    
    public void setAnnotateId(String annotateId)
    {
        this.annotateId = annotateId;
    }
    
    public String getMonitorTaskId()
    {
        return monitorTaskId;
    }
    
    public void setMonitorTaskId(String monitorTaskId)
    {
        this.monitorTaskId = monitorTaskId;
    }
    
    public Boolean getStatus()
    {
        return status;
    }
    
    public void setStatus(Boolean status)
    {
        this.status = status;
    }
    
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    public Date getEndTime()
    {
        return endTime;
    }
    
    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }
    
    public String getSampleTestId()
    {
        return sampleTestId;
    }
    
    public void setSampleTestId(String sampleTestId)
    {
        this.sampleTestId = sampleTestId;
    }
}