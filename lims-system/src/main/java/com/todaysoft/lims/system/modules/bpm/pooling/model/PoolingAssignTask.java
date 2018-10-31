package com.todaysoft.lims.system.modules.bpm.pooling.model;

import java.math.BigDecimal;

public class PoolingAssignTask
{
    private String id;
    
    private String testingCode;
    
    private String sampleTypeId;
    
    private String sampleTypeName;
    
    private String sampleCode;
    
    private boolean captureSample;
    
    private String probeName;
    
    private BigDecimal probeDatasize;
    
    private BigDecimal testingDatasize;
    
    private BigDecimal ctv;
    
    private BigDecimal rqtv;
    
    private BigDecimal rqtInputVolume;
    
    private BigDecimal concn;
    
    private String testingMethod;
    
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
    
    public String getSampleTypeId()
    {
        return sampleTypeId;
    }
    
    public void setSampleTypeId(String sampleTypeId)
    {
        this.sampleTypeId = sampleTypeId;
    }
    
    public String getSampleTypeName()
    {
        return sampleTypeName;
    }
    
    public void setSampleTypeName(String sampleTypeName)
    {
        this.sampleTypeName = sampleTypeName;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public boolean isCaptureSample()
    {
        return captureSample;
    }
    
    public void setCaptureSample(boolean captureSample)
    {
        this.captureSample = captureSample;
    }
    
    public String getProbeName()
    {
        return probeName;
    }
    
    public void setProbeName(String probeName)
    {
        this.probeName = probeName;
    }
    
    public BigDecimal getProbeDatasize()
    {
        return probeDatasize;
    }
    
    public void setProbeDatasize(BigDecimal probeDatasize)
    {
        this.probeDatasize = probeDatasize;
    }
    
    public BigDecimal getTestingDatasize()
    {
        return testingDatasize;
    }
    
    public void setTestingDatasize(BigDecimal testingDatasize)
    {
        this.testingDatasize = testingDatasize;
    }
    
    public BigDecimal getCtv()
    {
        return ctv;
    }
    
    public void setCtv(BigDecimal ctv)
    {
        this.ctv = ctv;
    }
    
    public BigDecimal getRqtv()
    {
        return rqtv;
    }
    
    public void setRqtv(BigDecimal rqtv)
    {
        this.rqtv = rqtv;
    }
    
    public BigDecimal getRqtInputVolume()
    {
        return rqtInputVolume;
    }
    
    public void setRqtInputVolume(BigDecimal rqtInputVolume)
    {
        this.rqtInputVolume = rqtInputVolume;
    }
    
    public BigDecimal getConcn()
    {
        return concn;
    }
    
    public void setConcn(BigDecimal concn)
    {
        this.concn = concn;
    }
    
    public String getTestingMethod()
    {
        return testingMethod;
    }
    
    public void setTestingMethod(String testingMethod)
    {
        this.testingMethod = testingMethod;
    }
}
