package com.todaysoft.lims.report.service.core.event;

import java.util.List;

import com.todaysoft.lims.report.model.TestingStartRecord;



public class ConcludingReportEvent
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
