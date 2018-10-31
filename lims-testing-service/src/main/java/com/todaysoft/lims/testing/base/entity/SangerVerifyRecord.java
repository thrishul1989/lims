package com.todaysoft.lims.testing.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_TESTING_SANGER_VERIFY")
public class SangerVerifyRecord extends UuidKeyEntity
{
    private static final long serialVersionUID = -5788208967238857493L;
    
    private TestingVerifyRecord verifyRecord;
    
    private String code;
    
    private Primer primer;
    
    private TestingSample dnaSample;
    
    @OneToOne
    @JoinColumn(name = "VERIFY_RECORD")
    @NotFound(action = NotFoundAction.IGNORE)
    public TestingVerifyRecord getVerifyRecord()
    {
        return verifyRecord;
    }
    
    public void setVerifyRecord(TestingVerifyRecord verifyRecord)
    {
        this.verifyRecord = verifyRecord;
    }
    
    @Column(name = "COMBINE_CODE")
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    @ManyToOne
    @JoinColumn(name = "PRIMER_ID")
    @NotFound(action = NotFoundAction.IGNORE)
    public Primer getPrimer()
    {
        return primer;
    }
    
    public void setPrimer(Primer primer)
    {
        this.primer = primer;
    }
    
    @ManyToOne
    @JoinColumn(name = "DNA_SAMPLE_ID")
    @NotFound(action = NotFoundAction.IGNORE)
    public TestingSample getDnaSample()
    {
        return dnaSample;
    }
    
    public void setDnaSample(TestingSample dnaSample)
    {
        this.dnaSample = dnaSample;
    }
}
