package com.todaysoft.lims.sample.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_INVOICE_SEND")
public class InvoiceSend extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private Date sendTime;
    
    private String transportType;
    
    private String trackNumber;
    
    private String transportName;
    
    private String transportPhone;
    
    private String sendDetail;
    
    private String operateId;
    private String operateName;
    private Date operateTime;
    
    @Column(name = "SEND_TIME")
    public Date getSendTime()
    {
        return sendTime;
    }
    
    public void setSendTime(Date sendTime)
    {
        this.sendTime = sendTime;
    }
    
    @Column(name = "TRANSPORT_TYPE")
    public String getTransportType()
    {
        return transportType;
    }
    
    public void setTransportType(String transportType)
    {
        this.transportType = transportType;
    }
    
    @Column(name = "TRACK_NUMBER")
    public String getTrackNumber()
    {
        return trackNumber;
    }
    
    public void setTrackNumber(String trackNumber)
    {
        this.trackNumber = trackNumber;
    }
    
    @Column(name = "TRANSPORT_NAME")
    public String getTransportName()
    {
        return transportName;
    }
    
    public void setTransportName(String transportName)
    {
        this.transportName = transportName;
    }
    
    @Column(name = "TRANSPORT_PHONE")
    public String getTransportPhone()
    {
        return transportPhone;
    }
    
    public void setTransportPhone(String transportPhone)
    {
        this.transportPhone = transportPhone;
    }
    
    @Column(name = "SEND_DETAIL")
    public String getSendDetail()
    {
        return sendDetail;
    }
    
    public void setSendDetail(String sendDetail)
    {
        this.sendDetail = sendDetail;
    }
    
    @Column(name = "OPERATE_ID")
    public String getOperateId()
    {
        return operateId;
    }

    public void setOperateId(String operateId)
    {
        this.operateId = operateId;
    }

    @Column(name = "OPERATE_NAME")
    public String getOperateName()
    {
        return operateName;
    }

    public void setOperateName(String operateName)
    {
        this.operateName = operateName;
    }

    @Column(name = "OPERATE_TIME")
    public Date getOperateTime()
    {
        return operateTime;
    }

    public void setOperateTime(Date operateTime)
    {
        this.operateTime = operateTime;
    }
}
