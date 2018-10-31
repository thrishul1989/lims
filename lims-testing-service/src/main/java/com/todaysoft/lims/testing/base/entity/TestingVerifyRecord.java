package com.todaysoft.lims.testing.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "LIMS_TESTING_TECHNICAL_ANALY_VERIFY")
public class TestingVerifyRecord extends UuidKeyEntity
{
    private static final long serialVersionUID = -1780195683554063783L;
    
    private TestingTechnicalAnalyRecord analyRecord;
    
    private TestingSample inputSample;
    
    private String inputSampleFamilyRelation;
    
    @ManyToOne
    @JoinColumn(name = "RECORD_ID")
    @NotFound(action = NotFoundAction.IGNORE)
    public TestingTechnicalAnalyRecord getAnalyRecord()
    {
        return analyRecord;
    }
    
    public void setAnalyRecord(TestingTechnicalAnalyRecord analyRecord)
    {
        this.analyRecord = analyRecord;
    }
    
    @ManyToOne
    @JoinColumn(name = "INPUT_SAMPLE_ID")
    @NotFound(action = NotFoundAction.IGNORE)
    public TestingSample getInputSample()
    {
        return inputSample;
    }
    
    public void setInputSample(TestingSample inputSample)
    {
        this.inputSample = inputSample;
    }
    
    @Column(name = "SAMPLE_FAMILY_RELATION")
    public String getInputSampleFamilyRelation()
    {
        return inputSampleFamilyRelation;
    }
    
    public void setInputSampleFamilyRelation(String inputSampleFamilyRelation)
    {
        this.inputSampleFamilyRelation = inputSampleFamilyRelation;
    }
}
