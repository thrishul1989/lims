package com.todaysoft.lims.system.modules.bpm.rqt.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

public class RQTTask
{
    private String id;
    
    private String testingCode;
    
    private String sampleTypeId;
    
    private String sampleTypeName;
    
    private String sampleCode;
    
    private boolean captureSample;
    
    private String probeName;
    
    private BigDecimal probeDatasize;
    
    private BigDecimal concn;
    
    private String testingMethod;
    
    private Set<CaptureLibraryAttributes> libraries;
    
    // 相对定量参考值
    private BigDecimal poolingConcn;
    
    private BigDecimal sampleInputVolume;
    
    private BigDecimal teInputVolume;
    
    private BigDecimal totalDatasize;
    
    private Integer ifUrgent;
    
    private Date urgentUpdateTime;
    
    private String urgentName;
    
    private String location;
    
    private Integer storageStatus;
    
    private Date startTime;
    
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
    
    public BigDecimal getTotalDatasize()
    {
        return totalDatasize;
    }
    
    public void setTotalDatasize(BigDecimal totalDatasize)
    {
        this.totalDatasize = totalDatasize;
    }
    
    public Set<CaptureLibraryAttributes> getLibraries()
    {
        return libraries;
    }
    
    public void setLibraries(Set<CaptureLibraryAttributes> libraries)
    {
        this.libraries = libraries;
    }

    public Integer getIfUrgent()
    {
        return ifUrgent;
    }

    public void setIfUrgent(Integer ifUrgent)
    {
        this.ifUrgent = ifUrgent;
    }

    public Date getUrgentUpdateTime()
    {
        return urgentUpdateTime;
    }

    public void setUrgentUpdateTime(Date urgentUpdateTime)
    {
        this.urgentUpdateTime = urgentUpdateTime;
    }

    public String getUrgentName()
    {
        return urgentName;
    }

    public void setUrgentName(String urgentName)
    {
        this.urgentName = urgentName;
    }

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public Integer getStorageStatus()
    {
        return storageStatus;
    }

    public void setStorageStatus(Integer storageStatus)
    {
        this.storageStatus = storageStatus;
    }

    public Date getStartTime()
    {
        return startTime;
    }

    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }
    
}
