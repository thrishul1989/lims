package com.todaysoft.lims.system.model.transation;

import com.todaysoft.lims.system.modules.bcm.model.TestingMethod;

public class TestingStartRecord
{
    
    private TestingOrder order;
    
    private TestingProduct product;
    
    private TestingMethod method;
    
    private OriginalSample sample;
    
    public TestingOrder getOrder()
    {
        return order;
    }
    
    public void setOrder(TestingOrder order)
    {
        this.order = order;
    }
    
    public TestingProduct getProduct()
    {
        return product;
    }
    
    public void setProduct(TestingProduct product)
    {
        this.product = product;
    }
    
    public TestingMethod getMethod()
    {
        return method;
    }
    
    public void setMethod(TestingMethod method)
    {
        this.method = method;
    }
    
    public OriginalSample getSample()
    {
        return sample;
    }
    
    public void setSample(OriginalSample sample)
    {
        this.sample = sample;
    }
    
}
