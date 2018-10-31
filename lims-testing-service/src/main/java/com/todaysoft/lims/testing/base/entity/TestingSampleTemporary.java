package com.todaysoft.lims.testing.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_TESTING_SAMPLE_TEMPORARY")
public class TestingSampleTemporary extends UuidKeyEntity
{
    
    private String sampleCode;
    
    private String sampleTypeId;
    
    private TestingSample parentSample;
    
    private TestingTask testingTask;
    
    @ManyToOne
    @JoinColumn(name = "TASK_ID")
    public TestingTask getTestingTask()
    {
        return testingTask;
    }

    public void setTestingTask(TestingTask testingTask)
    {
        this.testingTask = testingTask;
    }

    @Column(name = "SAMPLE_CODE")
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    @Column(name = "SAMPLE_TYPE_ID")
    public String getSampleTypeId()
    {
        return sampleTypeId;
    }
    
    public void setSampleTypeId(String sampleTypeId)
    {
        this.sampleTypeId = sampleTypeId;
    }
    
    @ManyToOne
    @JoinColumn(name = "PARENT_SAMPLE_ID")
    public TestingSample getParentSample()
    {
        return parentSample;
    }
    
    public void setParentSample(TestingSample parentSample)
    {
        this.parentSample = parentSample;
    }
    
}
