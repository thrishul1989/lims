package com.todaysoft.lims.testing.base.model;

import java.util.List;

public class ConcludingReportModel
{
    private String orderId;
    
    private List<TestingStartRecord> startRecors;
    
    public List<TestingStartRecord> getStartRecors()
    {
        return startRecors;
    }
    
    public void setStartRecors(List<TestingStartRecord> startRecors)
    {
        this.startRecors = startRecors;
    }
    
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
}
