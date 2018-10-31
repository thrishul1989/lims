package com.todaysoft.lims.testing.base.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_TESTING_CAPTURE_SAMPLE")
public class TestingCaptureSample extends UuidKeyEntity
{
    private static final long serialVersionUID = 1106549649263925947L;
    
    private TestingCaptureGroup group;
    
    private TestingSample sample;
    
    private BigDecimal inputSize;
    
    private BigDecimal inputVolume;
    
    private String testingCode;
    
    @Transient
    public String getTestingCode()
    {
        return testingCode;
    }
    
    public void setTestingCode(String testingCode)
    {
        this.testingCode = testingCode;
    }
    
    @ManyToOne
    @JoinColumn(name = "TASK_ID")
    public TestingCaptureGroup getGroup()
    {
        return group;
    }
    
    public void setGroup(TestingCaptureGroup group)
    {
        this.group = group;
    }
    
    @ManyToOne
    @JoinColumn(name = "SAMPLE_ID")
    public TestingSample getSample()
    {
        return sample;
    }
    
    public void setSample(TestingSample sample)
    {
        this.sample = sample;
    }
    
    @Column(name = "INPUT_SIZE")
    public BigDecimal getInputSize()
    {
        return inputSize;
    }
    
    public void setInputSize(BigDecimal inputSize)
    {
        this.inputSize = inputSize;
    }
    
    @Column(name = "INPUT_VOLUME")
    public BigDecimal getInputVolume()
    {
        return inputVolume;
    }
    
    public void setInputVolume(BigDecimal inputVolume)
    {
        this.inputVolume = inputVolume;
    }
}
