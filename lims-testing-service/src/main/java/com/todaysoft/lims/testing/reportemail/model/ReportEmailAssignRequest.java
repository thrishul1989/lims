package com.todaysoft.lims.testing.reportemail.model;

import java.util.List;

public class ReportEmailAssignRequest
{
    private String assignId;
    
    private String person;
    
    public String getAssignId()
    {
        return assignId;
    }
    
    public void setAssignId(String assignId)
    {
        this.assignId = assignId;
    }
    
    private List<String> orderIds;
    
    public String getPerson()
    {
        return person;
    }
    
    public void setPerson(String person)
    {
        this.person = person;
    }
    
    public List<String> getOrderIds()
    {
        return orderIds;
    }
    
    public void setOrderIds(List<String> orderIds)
    {
        this.orderIds = orderIds;
    }
}
