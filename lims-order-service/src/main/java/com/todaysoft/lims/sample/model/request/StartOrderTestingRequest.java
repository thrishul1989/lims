package com.todaysoft.lims.sample.model.request;

import java.util.Date;

public class StartOrderTestingRequest
{
    private String id;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    //ww
    private String cancelId;
    
    private String cancelName;
    
    private String cancelRemark;
    
    private Date cancelTime;

    public String getCancelId()
    {
        return cancelId;
    }

    public void setCancelId(String cancelId)
    {
        this.cancelId = cancelId;
    }

    public String getCancelName()
    {
        return cancelName;
    }

    public void setCancelName(String cancelName)
    {
        this.cancelName = cancelName;
    }

    public String getCancelRemark()
    {
        return cancelRemark;
    }

    public void setCancelRemark(String cancelRemark)
    {
        this.cancelRemark = cancelRemark;
    }

    public Date getCancelTime()
    {
        return cancelTime;
    }

    public void setCancelTime(Date cancelTime)
    {
        this.cancelTime = cancelTime;
    }
    
}
