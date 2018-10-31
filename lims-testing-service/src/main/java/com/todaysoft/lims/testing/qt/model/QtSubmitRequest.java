package com.todaysoft.lims.testing.qt.model;

import java.util.List;

public class QtSubmitRequest
{
    private String id;
    
    private QtSubmitSampleArgs primarySample;
    
    private List<QtSubmitSampleArgs> referenceSamples;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public QtSubmitSampleArgs getPrimarySample()
    {
        return primarySample;
    }
    
    public void setPrimarySample(QtSubmitSampleArgs primarySample)
    {
        this.primarySample = primarySample;
    }
    
    public List<QtSubmitSampleArgs> getReferenceSamples()
    {
        return referenceSamples;
    }
    
    public void setReferenceSamples(List<QtSubmitSampleArgs> referenceSamples)
    {
        this.referenceSamples = referenceSamples;
    }
}
