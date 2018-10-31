package com.todaysoft.lims.testing.libcre.model;

import java.math.BigDecimal;

import com.todaysoft.lims.testing.base.service.impl.TaskVariables;

public class LibraryCreateTaskVariables implements TaskVariables
{
    private String testingCode;
    
    private BigDecimal sampleInputSize;
    
    private BigDecimal sampleInputVolume;
    
    private BigDecimal teInputVolume;
    
    private BigDecimal testingInputVolume;
    
    private String libraryIndex;
    
    private String outputSampleTypeId;
    
    private String outputSampleTypeName;
    
    private String outputSampleCode;
    
    private BigDecimal outputSampleSize;
    
    private String location;

    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
    
    public String getOutputSampleTypeId()
    {
        return outputSampleTypeId;
    }
    
    public void setOutputSampleTypeId(String outputSampleTypeId)
    {
        this.outputSampleTypeId = outputSampleTypeId;
    }
    
    public String getOutputSampleTypeName()
    {
        return outputSampleTypeName;
    }
    
    public void setOutputSampleTypeName(String outputSampleTypeName)
    {
        this.outputSampleTypeName = outputSampleTypeName;
    }
    
    public String getOutputSampleCode()
    {
        return outputSampleCode;
    }
    
    public void setOutputSampleCode(String outputSampleCode)
    {
        this.outputSampleCode = outputSampleCode;
    }
    
    public BigDecimal getOutputSampleSize()
    {
        return outputSampleSize;
    }
    
    public void setOutputSampleSize(BigDecimal outputSampleSize)
    {
        this.outputSampleSize = outputSampleSize;
    }
    
    public String getLocation()
    {
        return location;
    }
    
    public void setLocation(String location)
    {
        this.location = location;
    }
}
