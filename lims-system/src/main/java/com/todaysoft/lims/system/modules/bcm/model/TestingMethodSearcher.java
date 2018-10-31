package com.todaysoft.lims.system.modules.bcm.model;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class TestingMethodSearcher
{
    private String name;
    
    private Integer type;
    
    
    public TestingMethodSearcher(){
        
    }
    public Integer getType()
    {
        return type;
    }

    public void setType(Integer type)
    {
        this.type = type;
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
