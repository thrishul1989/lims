package com.todaysoft.lims.sample.model;

public class DNAExtractVariables
{
    private String sampleCode;
    
    private String sampleName;
    
    private String sampleTypeId;
    
    private String sampleTypeName;
    
    private Long samplingDate;
    
    private String orderType;
    
    private String orderTypeName;

    private String location;//长存位置


    
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
