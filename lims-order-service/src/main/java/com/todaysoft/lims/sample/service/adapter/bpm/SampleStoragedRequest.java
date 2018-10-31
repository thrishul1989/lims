package com.todaysoft.lims.sample.service.adapter.bpm;

public class SampleStoragedRequest
{
    private String sampleCode;
    
    private String storagingType;
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public String getStoragingType()
    {
        return storagingType;
    }
    
    public void setStoragingType(String storagingType)
    {
        this.storagingType = storagingType;
    }
}
