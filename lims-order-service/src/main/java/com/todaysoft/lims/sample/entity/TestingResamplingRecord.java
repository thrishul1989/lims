package com.todaysoft.lims.sample.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_TESTING_RESAMPLING")
public class TestingResamplingRecord extends UuidKeyEntity
{
    private static final long serialVersionUID = -4947592536774231724L;
    
    public static final String STRATEGY_RESEND_CANCEL = "2";
    
    private String abnormalSampleId;
    
    private String strategy;
    
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
}
