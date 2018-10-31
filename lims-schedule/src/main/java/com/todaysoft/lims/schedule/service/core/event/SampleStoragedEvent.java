package com.todaysoft.lims.schedule.service.core.event;

public class SampleStoragedEvent
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
