package com.todaysoft.lims.system.modules.bpm.multipcrqc.model;

import com.todaysoft.lims.system.model.vo.Product;
import com.todaysoft.lims.system.modules.bpm.samplestock.model.TestingTask;

public class MultipcrqcTestModel
{
    
    private String testingCode;
    
    private Product product;
    
    private TestingTask testingTask;

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
