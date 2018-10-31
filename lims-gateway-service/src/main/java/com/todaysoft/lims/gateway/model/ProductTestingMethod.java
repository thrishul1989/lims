package com.todaysoft.lims.gateway.model;

import java.util.List;

public class ProductTestingMethod
{
    private String id;
    
    private Product product;
    
    private TestingMethod testingMethod;
    
    private List<ProductProbe> productProbe;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public Product getProduct()
    {
        return product;
    }
    
    public void setProduct(Product product)
    {
        this.product = product;
    }
    
    public TestingMethod getTestingMethod()
    {
        return testingMethod;
    }
    
    public void setTestingMethod(TestingMethod testingMethod)
    {
        this.testingMethod = testingMethod;
    }
    
    public List<ProductProbe> getProductProbe()
    {
        return productProbe;
    }
    
    public void setProductProbe(List<ProductProbe> productProbe)
    {
        this.productProbe = productProbe;
    }
}
