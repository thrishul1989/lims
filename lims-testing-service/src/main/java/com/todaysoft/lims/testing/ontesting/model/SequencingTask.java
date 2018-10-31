package com.todaysoft.lims.testing.ontesting.model;

import java.math.BigDecimal;
import java.util.Date;

public class SequencingTask
{
    private String id;
    
    private String sampleCode;
    
    private Date createTime;
    
    private BigDecimal testingDatasize;
    
    private Integer ifUrgent;
    
    private Date urgentUpdateTime;
    
    private String urgentName;
    
    private Date plannedFinishDate;
    
    public BigDecimal getTestingDatasize()
    {
        return testingDatasize;
    }
    
    public void setTestingDatasize(BigDecimal testingDatasize)
    {
        this.testingDatasize = testingDatasize;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public Integer getIfUrgent()
    {
        return ifUrgent;
    }

    public void setIfUrgent(Integer ifUrgent)
    {
        this.ifUrgent = ifUrgent;
    }

    public Date getUrgentUpdateTime()
    {
        return urgentUpdateTime;
    }

    public void setUrgentUpdateTime(Date urgentUpdateTime)
    {
        this.urgentUpdateTime = urgentUpdateTime;
    }

    public String getUrgentName()
    {
        return urgentName;
    }

    public void setUrgentName(String urgentName)
    {
        this.urgentName = urgentName;
    }

    public Date getPlannedFinishDate()
    {
        return plannedFinishDate;
    }

    public void setPlannedFinishDate(Date plannedFinishDate)
    {
        this.plannedFinishDate = plannedFinishDate;
    }
    
}
