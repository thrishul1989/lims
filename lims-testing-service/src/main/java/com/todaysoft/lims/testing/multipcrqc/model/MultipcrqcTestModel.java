package com.todaysoft.lims.testing.multipcrqc.model;

import com.todaysoft.lims.testing.base.entity.Product;
import com.todaysoft.lims.testing.base.entity.TestingTask;



public class MultipcrqcTestModel
{
    
    private String testingCode;
    
    private Product product;
    
    private TestingTask testingTask;
    
    private String location;

    public String getLocation()
    {
        return location;
    }

    public void setLocation(String location)
    {
        this.location = location;
    }

    public String getTestingCode()
    {
        return testingCode;
    }

    public void setTestingCode(String testingCode)
    {
        this.testingCode = testingCode;
    }

    public Product getProduct()
    {
        return product;
    }

    public void setProduct(Product product)
    {
        this.product = product;
    }

    public TestingTask getTestingTask()
    {
        return testingTask;
    }

    public void setTestingTask(TestingTask testingTask)
    {
        this.testingTask = testingTask;
    }
    
}
