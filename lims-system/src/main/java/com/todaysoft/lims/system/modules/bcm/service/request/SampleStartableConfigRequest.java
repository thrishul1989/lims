package com.todaysoft.lims.system.modules.bcm.service.request;

import java.util.List;

public class SampleStartableConfigRequest
{
    private List<String> startableSamples;
    
    public List<String> getStartableSamples()
    {
        return startableSamples;
    }
    
    public void setStartableSamples(List<String> startableSamples)
    {
        this.startableSamples = startableSamples;
    }
}
