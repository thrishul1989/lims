package com.todaysoft.lims.gateway.model;

import java.util.Date;

public class TestingSchedule
{
    private String sampleInstanceId;
    
    private String productId;
    
    private String processInstanceId;
    
    private String processStarter;
    
    private Date processStartTime;
    
    private Date processEndTime;
    
    private String status;
    
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
    
    public String getProcessInstanceId()
    {
        return processInstanceId;
    }
    
    public void setProcessInstanceId(String processInstanceId)
    {
        this.processInstanceId = processInstanceId;
    }
    
    public String getProcessStarter()
    {
        return processStarter;
    }
    
    public void setProcessStarter(String processStarter)
    {
        this.processStarter = processStarter;
    }
    
    public Date getProcessStartTime()
    {
        return processStartTime;
    }
    
    public void setProcessStartTime(Date processStartTime)
    {
        this.processStartTime = processStartTime;
    }
    
    public Date getProcessEndTime()
    {
        return processEndTime;
    }
    
    public void setProcessEndTime(Date processEndTime)
    {
        this.processEndTime = processEndTime;
    }
    
    public String getStatus()
    {
        return status;
    }
    
    public void setStatus(String status)
    {
        this.status = status;
    }
}
