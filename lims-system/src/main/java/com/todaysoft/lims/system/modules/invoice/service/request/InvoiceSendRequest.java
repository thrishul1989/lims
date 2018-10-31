package com.todaysoft.lims.system.modules.invoice.service.request;

import java.util.Date;

public class InvoiceSendRequest
{
    private Date sendTime;
    
    private String transportType;
    
    private String trackNumber;
    
    private String transportName;
    
    private String transportPhone;
    
    private String sendDetail;
    
    private String recordKeys;
    
    private String categorys;
    
    private String operateId;
    
    private String operateName;

    public String getOperateId()
    {
        return operateId;
    }

    public void setOperateId(String operateId)
    {
        this.operateId = operateId;
    }

    public String getOperateName()
    {
        return operateName;
    }

    public void setOperateName(String operateName)
    {
        this.operateName = operateName;
    }

    public Date getSendTime()
    {
        return sendTime;
    }

    public void setSendTime(Date sendTime)
    {
        this.sendTime = sendTime;
    }

    public String getTransportType()
    {
        return transportType;
    }

    public void setTransportType(String transportType)
    {
        this.transportType = transportType;
    }

    public String getTrackNumber()
    {
        return trackNumber;
    }

    public void setTrackNumber(String trackNumber)
    {
        this.trackNumber = trackNumber;
    }

    public String getTransportName()
    {
        return transportName;
    }

    public void setTransportName(String transportName)
    {
        this.transportName = transportName;
    }

    public String getTransportPhone()
    {
        return transportPhone;
    }

    public void setTransportPhone(String transportPhone)
    {
        this.transportPhone = transportPhone;
    }

    public String getSendDetail()
    {
        return sendDetail;
    }

    public void setSendDetail(String sendDetail)
    {
        this.sendDetail = sendDetail;
    }

    public String getRecordKeys()
    {
        return recordKeys;
    }

    public void setRecordKeys(String recordKeys)
    {
        this.recordKeys = recordKeys;
    }

    public String getCategorys()
    {
        return categorys;
    }

    public void setCategorys(String categorys)
    {
        this.categorys = categorys;
    }
    
}
