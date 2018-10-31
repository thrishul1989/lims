package com.todaysoft.lims.product.model.request;

public class SampleSearchRequest
{
    private String keywords;
    
    private Integer type;
    
    private Boolean startable;
    
    public String getKeywords()
    {
        return keywords;
    }
    
    public void setKeywords(String keywords)
    {
        this.keywords = keywords;
    }
    
    public Integer getType()
    {
        return type;
    }
    
    public void setType(Integer type)
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
