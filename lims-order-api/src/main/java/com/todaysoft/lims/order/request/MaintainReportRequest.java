package com.todaysoft.lims.order.request;

import java.util.Date;
import java.util.List;

public class MaintainReportRequest
{
    private String id;
    
    private Date executeTime;
    
    private List<TestingReportModel> data;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public Date getExecuteTime()
    {
        return executeTime;
    }
    
    public void setExecuteTime(Date executeTime)
    {
        this.executeTime = executeTime;
    }
    
    public List<TestingReportModel> getData()
    {
        return data;
    }
    
    public void setData(List<TestingReportModel> data)
    {
        this.data = data;
    }
    
}
