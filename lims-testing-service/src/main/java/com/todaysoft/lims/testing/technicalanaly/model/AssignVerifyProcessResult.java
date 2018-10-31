package com.todaysoft.lims.testing.technicalanaly.model;

import java.util.List;
import java.util.Set;

//用于发送排计划的消息
public class AssignVerifyProcessResult
{
    private String dataCode;
    
    private List<String> schduleIds;
    
    private Set<String> orderIds;
    
    public List<String> getSchduleIds()
    {
        return schduleIds;
    }
    public void setSchduleIds(List<String> schduleIds)
    {
        this.schduleIds = schduleIds;
    }
    public Set<String> getOrderIds()
    {
        return orderIds;
    }
    public void setOrderIds(Set<String> orderIds)
    {
        this.orderIds = orderIds;
    }
    public String getDataCode()
    {
        return dataCode;
    }
    public void setDataCode(String dataCode)
    {
        this.dataCode = dataCode;
    }
    
    
}
