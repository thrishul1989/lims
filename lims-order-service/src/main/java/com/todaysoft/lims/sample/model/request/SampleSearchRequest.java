package com.todaysoft.lims.sample.model.request;

/**
 * Created by HSHY-032 on 2016/10/31.
 */
public class SampleSearchRequest
{
    
    private String orderId;
    
    private String businessType;//业务类型：1-非科研主样本 2-非科研附属样本 3-科研样本
    
    private String sampleId;
    
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
    
    public String getBusinessType()
    {
        return businessType;
    }
    
    public void setBusinessType(String businessType)
    {
        this.businessType = businessType;
    }
    
    public String getSampleId()
    {
        return sampleId;
    }
    
    public void setSampleId(String sampleId)
    {
        this.sampleId = sampleId;
    }
}
