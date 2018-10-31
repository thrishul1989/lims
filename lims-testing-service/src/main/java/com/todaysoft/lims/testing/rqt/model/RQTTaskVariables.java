package com.todaysoft.lims.testing.rqt.model;

import java.math.BigDecimal;

public class RQTTaskVariables
{
    private String testingCode;
    
    private BigDecimal poolingConcn;
    
    private BigDecimal sampleInputVolume;
    
    private BigDecimal teInputVolume;
    
    private BigDecimal testingDatasize;
    
    public String getTestingCode()
    {
        return testingCode;
    }
    
    public void setTestingCode(String testingCode)
    {
        this.testingCode = testingCode;
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
}
