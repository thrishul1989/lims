package com.todaysoft.lims.sample.service.adapter.bpm;

import java.util.List;

public class SampleReceivedEvent
{
    private List<SampleReceivedDetails> details;
    
    public List<SampleReceivedDetails> getDetails()
    {
        return details;
    }
    
    public void setDetails(List<SampleReceivedDetails> details)
    {
        this.details = details;
    }
}
