package com.todaysoft.lims.biology.model.request;


public class FilterItemsRequest
{
    private String semantic;
    
    private String name;
    
    public String getSemantic()
    {
        return semantic;
    }
    
    public void setSemantic(String semantic)
    {
        this.semantic = semantic;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
}
