package com.todaysoft.lims.maintain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_TESTING_QPCR_VERIFY")
public class QpcrVerifyRecord extends UuidKeyEntity
{
    private static final long serialVersionUID = -5788208967238857493L;
    
    private TestingVerifyRecord verifyRecord;
    
    private String combineCode;
    
    
    private TestingSample dnaSample;
    
    @ManyToOne
    @JoinColumn(name = "DNA_SAMPLE_ID")
    public TestingSample getDnaSample()
    {
        return dnaSample;
    }

    public void setDnaSample(TestingSample dnaSample)
    {
        this.dnaSample = dnaSample;
    }

    @OneToOne
    @JoinColumn(name = "VERIFY_RECORD")
    public TestingVerifyRecord getVerifyRecord()
    {
        return verifyRecord;
    }
    
    public void setVerifyRecord(TestingVerifyRecord verifyRecord)
    {
        this.verifyRecord = verifyRecord;
    }
    
    @Column(name = "COMBINE_CODE")
    public String getCombineCode()
    {
        return combineCode;
    }
    
    public void setCombineCode(String combineCode)
    {
        this.combineCode = combineCode;
    }
    
}
