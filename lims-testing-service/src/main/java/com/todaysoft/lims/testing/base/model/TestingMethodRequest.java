package com.todaysoft.lims.testing.base.model;

public class TestingMethodRequest
{
    private String id;
    
    private String name;
    
    private String type;
    
    private boolean capture;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getType()
    {
        return type;
    }
    
    public void setType(String type)
    {
        this.type = type;
    }
    
    public boolean isCapture()
    {
        return capture;
    }
    
    public void setCapture(boolean capture)
    {
        this.capture = capture;
    }
    
}
