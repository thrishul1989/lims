package com.todaysoft.lims.system.modules.bpm.libcap.model;

import java.math.BigDecimal;

public class LibraryCaptureAssignTaskArgs
{
    private String id;
    
    private BigDecimal sampleInputSize;
    
    private BigDecimal sampleInputVolume;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public BigDecimal getSampleInputSize()
    {
        return sampleInputSize;
    }
    
    public void setSampleInputSize(BigDecimal sampleInputSize)
    {
        this.sampleInputSize = sampleInputSize;
    }
    
    public BigDecimal getSampleInputVolume()
    {
        return sampleInputVolume;
    }
    
    public void setSampleInputVolume(BigDecimal sampleInputVolume)
    {
        this.sampleInputVolume = sampleInputVolume;
    }
}
