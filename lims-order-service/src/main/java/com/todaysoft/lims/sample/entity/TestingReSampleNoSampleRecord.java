package com.todaysoft.lims.sample.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_TESTING_RESAMPLE_NOSAMPLE_RECORD")
public class TestingReSampleNoSampleRecord extends UuidKeyEntity
{
    private static final long serialVersionUID = 7746369371481516936L;
    
    private String sampleId;
    
    private String errorDealRemark;
    
    private Integer sampleErrorStatus;
    
    private String errorOperatorId;
    
    private String errorOperatorName;
    
    private Date errorOperatorTime;

    @Column(name="SAMPLE_ID")
    public String getSampleId()
    {
        return sampleId;
    }

    public void setSampleId(String sampleId)
    {
        this.sampleId = sampleId;
    }

    @Column(name="ERROR_DEAL_REMARK")
    public String getErrorDealRemark()
    {
        return errorDealRemark;
    }

    public void setErrorDealRemark(String errorDealRemark)
    {
        this.errorDealRemark = errorDealRemark;
    }

    @Column(name="SAMPLE_ERROR_STATUS")
    public Integer getSampleErrorStatus()
    {
        return sampleErrorStatus;
    }

    public void setSampleErrorStatus(Integer sampleErrorStatus)
    {
        this.sampleErrorStatus = sampleErrorStatus;
    }

    @Column(name="ERROR_OPERATOR_ID")
    public String getErrorOperatorId()
    {
        return errorOperatorId;
    }

    public void setErrorOperatorId(String errorOperatorId)
    {
        this.errorOperatorId = errorOperatorId;
    }

    @Column(name="ERROR_OPERATOR_NAME")
    public String getErrorOperatorName()
    {
        return errorOperatorName;
    }

    public void setErrorOperatorName(String errorOperatorName)
    {
        this.errorOperatorName = errorOperatorName;
    }

    @Column(name="ERROR_OPERATOR_TIME")
    public Date getErrorOperatorTime()
    {
        return errorOperatorTime;
    }

    public void setErrorOperatorTime(Date errorOperatorTime)
    {
        this.errorOperatorTime = errorOperatorTime;
    }
    
    
    
}
