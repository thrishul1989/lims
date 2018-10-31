package com.todaysoft.lims.sample.model;

public class TestingOrder
{
    private String id;
    
    private String code;
    
    private String type;
    
    public boolean isResearch()
    {
        return false;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public String getType()
    {
        return type;
    }
    
    public void setType(String type)
    {
        this.type = type;
    }
}
