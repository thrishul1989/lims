package com.todaysoft.lims.testing.biologyanaly.context;

import com.todaysoft.lims.testing.base.entity.ReceivedSample;

public class BiologyAnalySubmitLibraryContext
{
    private String sampleCode;
    
    private String index;
    
    private ReceivedSample receivedSampleEntity;
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public String getIndex()
    {
        return index;
    }
    
    public void setIndex(String index)
    {
        this.index = index;
    }
    
    public ReceivedSample getReceivedSampleEntity()
    {
        return receivedSampleEntity;
    }
    
    public void setReceivedSampleEntity(ReceivedSample receivedSampleEntity)
    {
        this.receivedSampleEntity = receivedSampleEntity;
    }
}
