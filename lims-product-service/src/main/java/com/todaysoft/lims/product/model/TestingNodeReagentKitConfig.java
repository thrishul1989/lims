package com.todaysoft.lims.product.model;

import java.math.BigDecimal;

public class TestingNodeReagentKitConfig
{
    private String id;
    
    private String name;
    
    private BigDecimal sampleInputQuantity;
    
    private BigDecimal sampleInputVolume;
    
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
    
    public BigDecimal getSampleInputQuantity()
    {
        return sampleInputQuantity;
    }
    
    public void setSampleInputQuantity(BigDecimal sampleInputQuantity)
    {
        this.sampleInputQuantity = sampleInputQuantity;
    }
    
    public BigDecimal getSampleInputVolume()
    {
        return sampleInputVolume;
    }
    
    public void setSampleInputVolume(BigDecimal sampleInputVolume)
    {
        this.sampleInputVolume = sampleInputVolume;
    }
}
