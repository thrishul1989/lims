package com.todaysoft.lims.schedule.model;

public class TestingPlanTask extends PlanTask
{
    private String productId;
    
    private String sampleId;
    
    private String testingMethodId;
    
    public String getProductId()
    {
        return productId;
    }
    
    public void setProductId(String productId)
    {
        this.productId = productId;
    }
    
    public String getSampleId()
    {
        return sampleId;
    }
    
    public void setSampleId(String sampleId)
    {
        this.sampleId = sampleId;
    }
    
    public String getTestingMethodId()
    {
        return testingMethodId;
    }
    
    public void setTestingMethodId(String testingMethodId)
    {
        this.testingMethodId = testingMethodId;
    }
}
