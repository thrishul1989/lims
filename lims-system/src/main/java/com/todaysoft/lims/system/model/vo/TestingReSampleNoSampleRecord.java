package com.todaysoft.lims.system.model.vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

public class TestingReSampleNoSampleRecord 
{
    
    private String sampleId;
    
    private String errorDealRemark;
    
    private Integer sampleErrorStatus;
    
    private String errorOperatorId;
    
    private String errorOperatorName;
    
    private Date errorOperatorTime;

    public String getSampleId()
    {
        return sampleId;
    }

    public void setSampleId(String sampleId)
    {
        this.sampleId = sampleId;
    }

    public String getErrorDealRemark()
    {
        return errorDealRemark;
    }

    public void setErrorDealRemark(String errorDealRemark)
    {
        this.errorDealRemark = errorDealRemark;
    }

    public Integer getSampleErrorStatus()
    {
        return sampleErrorStatus;
    }

    public void setSampleErrorStatus(Integer sampleErrorStatus)
    {
        this.sampleErrorStatus = sampleErrorStatus;
    }

    public String getErrorOperatorId()
    {
        return errorOperatorId;
    }

    public void setErrorOperatorId(String errorOperatorId)
    {
        this.errorOperatorId = errorOperatorId;
    }

    public String getErrorOperatorName()
    {
        return errorOperatorName;
    }

    public void setErrorOperatorName(String errorOperatorName)
    {
        this.errorOperatorName = errorOperatorName;
    }

    public Date getErrorOperatorTime()
    {
        return errorOperatorTime;
    }

    public void setErrorOperatorTime(Date errorOperatorTime)
    {
        this.errorOperatorTime = errorOperatorTime;
    }
    
    
    
}
