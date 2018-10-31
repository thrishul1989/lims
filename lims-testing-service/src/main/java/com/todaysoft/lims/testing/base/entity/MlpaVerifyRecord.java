package com.todaysoft.lims.testing.base.entity;

import com.todaysoft.lims.persist.UuidKeyEntity;

import javax.persistence.*;

@Entity
@Table(name = "LIMS_TESTING_MLPA_VERIFY")
public class MlpaVerifyRecord extends UuidKeyEntity
{
    private static final long serialVersionUID = 110772204591426810L;
    
    private TestingVerifyRecord verifyRecord;
    
    private TestingSample dnaSample;

    private String combineCode;

    @Column(name = "COMBINE_CODE")
    public String getCombineCode()
    {
        return combineCode;
    }
    
    public void setCombineCode(String combineCode)
    {
        this.combineCode = combineCode;
    }

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
    
}
