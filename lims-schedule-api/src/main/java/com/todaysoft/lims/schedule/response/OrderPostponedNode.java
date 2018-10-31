package com.todaysoft.lims.schedule.response;

public class OrderPostponedNode
{
    private String name;
    
    private int postponedDays;
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public int getPostponedDays()
    {
        return postponedDays;
    }
    
    public void setPostponedDays(int postponedDays)
    {
        this.postponedDays = postponedDays;
    }
}
