package com.todaysoft.lims.testing.cycleConfig.service.request;

public class GlobalConfigRequest
{
    private String eventKey;
    
    private int threshold;

    public String getEventKey()
    {
        return eventKey;
    }

    public void setEventKey(String eventKey)
    {
        this.eventKey = eventKey;
    }

    public int getThreshold()
    {
        return threshold;
    }

    public void setThreshold(int threshold)
    {
        this.threshold = threshold;
    }
    
    
}
