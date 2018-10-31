package com.todaysoft.lims.testing.rqt.context;

import java.math.BigDecimal;

import com.todaysoft.lims.testing.base.entity.TestingTask;

public class RQTSubmitTaskContext
{
    private String testingCode;
    
    private BigDecimal ctv;
    
    private BigDecimal poolingConcn;
    
    private BigDecimal sampleInputVolume;
    
    private BigDecimal teInputVolume;
    
    private BigDecimal testingDatasize;
    
    private TestingTask testingTaskEntity;
    
    public String getTestingCode()
    {
        return testingCode;
    }
    
    public void setTestingCode(String testingCode)
    {
        this.testingCode = testingCode;
    }
    
    public BigDecimal getCtv()
    {
        return ctv;
    }
    
    public void setCtv(BigDecimal ctv)
    {
        this.ctv = ctv;
    }
    
    public BigDecimal getPoolingConcn()
    {
        return poolingConcn;
    }
    
    public void setPoolingConcn(BigDecimal poolingConcn)
    {
        this.poolingConcn = poolingConcn;
    }
    
    public BigDecimal getSampleInputVolume()
    {
        return sampleInputVolume;
    }
    
    public void setSampleInputVolume(BigDecimal sampleInputVolume)
    {
        this.sampleInputVolume = sampleInputVolume;
    }
    
    public BigDecimal getTeInputVolume()
    {
        return teInputVolume;
    }
    
    public void setTeInputVolume(BigDecimal teInputVolume)
    {
        this.teInputVolume = teInputVolume;
    }
    
    public BigDecimal getTestingDatasize()
    {
        return testingDatasize;
    }
    
    public void setTestingDatasize(BigDecimal testingDatasize)
    {
        this.testingDatasize = testingDatasize;
    }
    
    public TestingTask getTestingTaskEntity()
    {
        return testingTaskEntity;
    }
    
    public void setTestingTaskEntity(TestingTask testingTaskEntity)
    {
        this.testingTaskEntity = testingTaskEntity;
    }
}
