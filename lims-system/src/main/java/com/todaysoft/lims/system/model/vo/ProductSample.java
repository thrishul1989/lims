package com.todaysoft.lims.system.model.vo;

import com.todaysoft.lims.system.modules.bcm.model.MetadataSample;

public class ProductSample
{
    
    private String id;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    private Product product;
    
    private MetadataSample sample;
    
    public Product getProduct()
    {
        return product;
    }
    
    public void setProduct(Product product)
    {
        this.product = product;
    }
    
    public MetadataSample getSample()
    {
        return sample;
    }
    
    public void setSample(MetadataSample sample)
    {
        this.sample = sample;
    }
}
