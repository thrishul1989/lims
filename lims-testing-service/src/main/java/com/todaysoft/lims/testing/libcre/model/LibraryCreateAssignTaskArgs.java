package com.todaysoft.lims.testing.libcre.model;

import java.math.BigDecimal;

public class LibraryCreateAssignTaskArgs
{
    private String id;
    
    private String testingCode;
    
    private BigDecimal sampleInputSize;
    
    private BigDecimal sampleInputVolume;
    
    private BigDecimal teInputVolume;
    
    private BigDecimal testingInputVolume;
    
    private String libraryIndex;
    
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
    
    public BigDecimal getTeInputVolume()
    {
        return teInputVolume;
    }
    
    public void setTeInputVolume(BigDecimal teInputVolume)
    {
        this.teInputVolume = teInputVolume;
    }
    
    public BigDecimal getTestingInputVolume()
    {
        return testingInputVolume;
    }
    
    public void setTestingInputVolume(BigDecimal testingInputVolume)
    {
        this.testingInputVolume = testingInputVolume;
    }
    
    public String getLibraryIndex()
    {
        return libraryIndex;
    }
    
    public void setLibraryIndex(String libraryIndex)
    {
        this.libraryIndex = libraryIndex;
    }
}
