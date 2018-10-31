package com.todaysoft.lims.sample.model.contract;

public class SuccessDelivery
{
    private String deliveryMode;//交付形式
    private String deliveryResult;//交付方式
    public String getDeliveryMode()
    {
        return deliveryMode;
    }
    public void setDeliveryMode(String deliveryMode)
    {
        this.deliveryMode = deliveryMode;
    }
    public String getDeliveryResult()
    {
        return deliveryResult;
    }
    public void setDeliveryResult(String deliveryResult)
    {
        this.deliveryResult = deliveryResult;
    }
    
}
