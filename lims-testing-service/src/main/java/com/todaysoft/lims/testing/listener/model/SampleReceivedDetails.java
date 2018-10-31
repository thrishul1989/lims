package com.todaysoft.lims.testing.listener.model;

public class SampleReceivedDetails
{
    private String sampleId;
    
    private boolean qualified;
    
    public String getSampleId()
    {
        return sampleId;
    }
    
    public void setSampleId(String sampleId)
    {
        this.sampleId = sampleId;
    }
    
    public boolean isQualified()
    {
        return qualified;
    }
    
    public void setQualified(boolean qualified)
    {
        this.qualified = qualified;
    }
}
