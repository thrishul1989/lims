package com.todaysoft.lims.system.modules.bcm.service.request;

public class SampleSearchRequest
{
    private String keywords;
    
    private String type;
    
    private Boolean startable;
    
    public String getKeywords()
    {
        return keywords;
    }
    
    public void setKeywords(String keywords)
    {
        this.keywords = keywords;
    }
    
    public String getType()
    {
        return type;
    }
    
    public void setType(String type)
    {
        this.type = type;
    }
    
    public Boolean getStartable()
    {
        return startable;
    }
    
    public void setStartable(Boolean startable)
    {
        this.startable = startable;
    }
}
