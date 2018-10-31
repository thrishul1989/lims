package com.todaysoft.lims.system.modules.bpm.samplestock.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

public class TestingCaptureSample
{
    private String id;
    
    private TestingCaptureTask task;
    
    private TestingSample sample;
    
    private BigDecimal inputSize;
    
    private BigDecimal inputVolume;
    
    private String testingCode;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getTestingCode()
    {
        return testingCode;
    }
    
    public void setTestingCode(String testingCode)
    {
        this.testingCode = testingCode;
    }
    
    public TestingCaptureTask getTask()
    {
        return task;
    }
    
    public void setTask(TestingCaptureTask task)
    {
        this.task = task;
    }
    
    public TestingSample getSample()
    {
        return sample;
    }
    
    public void setSample(TestingSample sample)
    {
        this.sample = sample;
    }
    
    public BigDecimal getInputSize()
    {
        return inputSize;
    }
    
    public void setInputSize(BigDecimal inputSize)
    {
        this.inputSize = inputSize;
    }
    
    public BigDecimal getInputVolume()
    {
        return inputVolume;
    }
    
    public void setInputVolume(BigDecimal inputVolume)
    {
        this.inputVolume = inputVolume;
    }
}
