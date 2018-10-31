package com.todaysoft.lims.testing.dnaext.model;

import java.math.BigDecimal;

import com.todaysoft.lims.testing.base.service.impl.TaskVariables;

public class DNAExtractTaskVariables implements TaskVariables
{
    private String testingCode;
    
    private BigDecimal sampleInputSize;
    
    private String outputSampleTypeId;
    
    private String outputSampleTypeName;
    
    private String outputSampleCode;
    
    private BigDecimal outputSampleSize;
    
    private String location;

    private String remark;
    
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
    
    public BigDecimal getOutputSampleSize()
    {
        return outputSampleSize;
    }
    
    public void setOutputSampleSize(BigDecimal outputSampleSize)
    {
        this.outputSampleSize = outputSampleSize;
    }
    
    public String getOutputSampleTypeId()
    {
        return outputSampleTypeId;
    }
    
    public void setOutputSampleTypeId(String outputSampleTypeId)
    {
        this.outputSampleTypeId = outputSampleTypeId;
    }
    
    public String getOutputSampleCode()
    {
        return outputSampleCode;
    }
    
    public void setOutputSampleCode(String outputSampleCode)
    {
        this.outputSampleCode = outputSampleCode;
    }
    
    public String getOutputSampleTypeName()
    {
        return outputSampleTypeName;
    }
    
    public void setOutputSampleTypeName(String outputSampleTypeName)
    {
        this.outputSampleTypeName = outputSampleTypeName;
    }
    
    public String getLocation()
    {
        return location;
    }
    
    public void setLocation(String location)
    {
        this.location = location;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
