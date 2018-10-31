package com.todaysoft.lims.report.entity.system;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_TESTING_RESAMPLING")
public class TestingResamplingRecord extends UuidKeyEntity
{
    private static final long serialVersionUID = -4947592536774231724L;
    
    public static final String STRATEGY_RESAMPLING = "1";
    
    public static final String STRATEGY_RESAMPLING_CANCEL = "2";
    
    public static final String STRATEGY_RESAMPLING_CANCEL_TESTING_CANCEL = "3";
    
    public static final String STRATEGY_RESAMPLING_CANCEL_TESTING_RISKING = "4";
    
    public static final String RESEND_SAMPLE_UNRECEIVED = "1";
    
    public static final String RESEND_SAMPLE_RECEIVED = "2";
    
    public static final String RESEND_SAMPLE_ABNORMAL = "3";
    
    private String abnormalSampleId;
    
    private String strategy;
    
    private String resendSampleId;
    
    private String resendSampleStatus;
    
    @Column(name = "ABNORMAL_SAMPLE_ID")
    public String getAbnormalSampleId()
    {
        return abnormalSampleId;
    }
    
    public void setAbnormalSampleId(String abnormalSampleId)
    {
        this.abnormalSampleId = abnormalSampleId;
    }
    
    @Column(name = "STRATEGY")
    public String getStrategy()
    {
        return strategy;
    }
    
    public void setStrategy(String strategy)
    {
        this.strategy = strategy;
    }
    
    @Column(name = "RESEND_SAMPLE_ID")
    public String getResendSampleId()
    {
        return resendSampleId;
    }
    
    public void setResendSampleId(String resendSampleId)
    {
        this.resendSampleId = resendSampleId;
    }
    
    @Column(name = "RESEND_SAMPLE_STATUS")
    public String getResendSampleStatus()
    {
        return resendSampleStatus;
    }
    
    public void setResendSampleStatus(String resendSampleStatus)
    {
        this.resendSampleStatus = resendSampleStatus;
    }
}
