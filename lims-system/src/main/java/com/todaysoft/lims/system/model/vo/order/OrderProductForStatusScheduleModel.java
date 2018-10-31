package com.todaysoft.lims.system.model.vo.order;

import java.util.List;

import com.todaysoft.lims.order.response.OrderStatusScheduleModel;

public class OrderProductForStatusScheduleModel
{
    private String productName;
    private List<OrderStatusScheduleModel> testingsamples;
    private List<OrderStatusScheduleModel> verificationSamples;
    public String getProductName()
    {
        return productName;
    }
    public void setProductName(String productName)
    {
        this.productName = productName;
    }
    public List<OrderStatusScheduleModel> getTestingsamples()
    {
        return testingsamples;
    }
    public void setTestingsamples(List<OrderStatusScheduleModel> testingsamples)
    {
        this.testingsamples = testingsamples;
    }
    public List<OrderStatusScheduleModel> getVerificationSamples()
    {
        return verificationSamples;
    }
    public void setVerificationSamples(List<OrderStatusScheduleModel> verificationSamples)
    {
        this.verificationSamples = verificationSamples;
    }
    
}
