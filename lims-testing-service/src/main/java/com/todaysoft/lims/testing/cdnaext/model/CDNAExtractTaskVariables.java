package com.todaysoft.lims.testing.cdnaext.model;

import java.math.BigDecimal;

public class CDNAExtractTaskVariables
{
    private String sampleCode;
    
    private String sampleName;
    
    private String sampleTypeId;
    
    private String sampleTypeName;
    
    private Long samplingDate;
    
    private String orderType;
    
    private String orderTypeName;
    
    private String testingCode;
    
    private BigDecimal sampleInputSize;
    
    private String location;
    
    private String outputSampleTypeId;
    
    private String outputSampleTypeName;
    
    private String outputSampleCode;
    
    private BigDecimal outputSampleSize;

    private String remark;
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public String getSampleName()
    {
        return sampleName;
    }
    
    public void setSampleName(String sampleName)
    {
        this.sampleName = sampleName;
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
    
    public Long getSamplingDate()
    {
        return samplingDate;
    }
    
    public void setSamplingDate(Long samplingDate)
    {
        this.samplingDate = samplingDate;
    }
    
    public String getOrderType()
    {
        return orderType;
    }
    
    public void setOrderType(String orderType)
    {
        this.orderType = orderType;
    }
    
    public String getOrderTypeName()
    {
        return orderTypeName;
    }
    
    public void setOrderTypeName(String orderTypeName)
    {
        this.orderTypeName = orderTypeName;
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
    
    public String getLocation()
    {
        return location;
    }
    
    public void setLocation(String location)
    {
        this.location = location;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
