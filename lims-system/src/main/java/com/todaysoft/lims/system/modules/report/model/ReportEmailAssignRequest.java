package com.todaysoft.lims.system.modules.report.model;

import java.util.List;

public class ReportEmailAssignRequest
{
    private String person;
    
    private String assignId;
    
    private List<String> orderIds;
    
    public String getAssignId()
    {
        return assignId;
    }

    public void setAssignId(String assignId)
    {
        this.assignId = assignId;
    }

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
