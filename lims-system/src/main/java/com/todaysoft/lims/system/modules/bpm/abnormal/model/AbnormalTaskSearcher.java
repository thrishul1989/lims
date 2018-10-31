package com.todaysoft.lims.system.modules.bpm.abnormal.model;

public class AbnormalTaskSearcher
{
    private String marketingCenter;

    private String taskName;
    
    private String orderCode;
    
    private String productName;
    
    private String receivedSampleCode;
    
    private String receivedSampleName;
    
    public String getMarketingCenter()
    {
        return marketingCenter;
    }
    
    public void setMarketingCenter(String marketingCenter)
    {
        this.marketingCenter = marketingCenter;
    }
    
    public String getOrderCode()
    {
        return orderCode;
    }
    
    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }
    
    public String getProductName()
    {
        return productName;
    }
    
    public void setProductName(String productName)
    {
        this.productName = productName;
    }
    
    public String getReceivedSampleCode()
    {
        return receivedSampleCode;
    }
    
    public void setReceivedSampleCode(String receivedSampleCode)
    {
        this.receivedSampleCode = receivedSampleCode;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getReceivedSampleName()
    {
        return receivedSampleName;
    }

    public void setReceivedSampleName(String receivedSampleName)
    {
        this.receivedSampleName = receivedSampleName;
    }
    
}
