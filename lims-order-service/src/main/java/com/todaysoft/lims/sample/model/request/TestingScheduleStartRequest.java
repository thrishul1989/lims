package com.todaysoft.lims.sample.model.request;

public class TestingScheduleStartRequest
{
    private String sampleInstanceId;
    
    private String productId;
    
    private String starter;

    private String orderId;
    
    public String getSampleInstanceId()
    {
        return sampleInstanceId;
    }
    
    public void setSampleInstanceId(String sampleInstanceId)
    {
        this.sampleInstanceId = sampleInstanceId;
    }
    
    public String getProductId()
    {
        return productId;
    }
    
    public void setProductId(String productId)
    {
        this.productId = productId;
    }
    
    public String getStarter()
    {
        return starter;
    }
    
    public void setStarter(String starter)
    {
        this.starter = starter;
    }

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}


  
}
