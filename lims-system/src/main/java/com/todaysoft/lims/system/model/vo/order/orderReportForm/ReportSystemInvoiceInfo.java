package com.todaysoft.lims.system.model.vo.order.orderReportForm;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

public class ReportSystemInvoiceInfo extends UuidKeyEntity
{
    private static final long serialVersionUID = 2511940266797707570L;
    
    private String taskId;
    //private String orderId;
    private String drawerNo;
    private String institution;
    private String amount;
    private String drawerName;
    private String invoiceTime;
    private String receiverName;
    private String sendStatus;
    private String transportType;
    private String trackNumber;
    private String operateName;
    private String sendTime;
    private ReportSystemOrderInfo orderInfo;
    
    
    public String getTaskId()
    {
        return taskId;
    }
    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }
    
    public ReportSystemOrderInfo getOrderInfo()
    {
        return orderInfo;
    }
    public void setOrderInfo(ReportSystemOrderInfo orderInfo)
    {
        this.orderInfo = orderInfo;
    }
    
    public String getDrawerNo()
    {
        return drawerNo;
    }
    public void setDrawerNo(String drawerNo)
    {
        this.drawerNo = drawerNo;
    }
    
    public String getInstitution()
    {
        return institution;
    }
    public void setInstitution(String institution)
    {
        this.institution = institution;
    }
    
    public String getAmount()
    {
        return amount;
    }
    public void setAmount(String amount)
    {
        this.amount = amount;
    }
    
    public String getDrawerName()
    {
        return drawerName;
    }
    public void setDrawerName(String drawerName)
    {
        this.drawerName = drawerName;
    }
    
    public String getInvoiceTime()
    {
        return invoiceTime;
    }
    public void setInvoiceTime(String invoiceTime)
    {
        this.invoiceTime = invoiceTime;
    }
    
    public String getReceiverName()
    {
        return receiverName;
    }
    public void setReceiverName(String receiverName)
    {
        this.receiverName = receiverName;
    }
    
    public String getSendStatus()
    {
        return sendStatus;
    }
    public void setSendStatus(String sendStatus)
    {
        this.sendStatus = sendStatus;
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
    
    public String getOperateName()
    {
        return operateName;
    }
    public void setOperateName(String operateName)
    {
        this.operateName = operateName;
    }
    
    public String getSendTime()
    {
        return sendTime;
    }
    public void setSendTime(String sendTime)
    {
        this.sendTime = sendTime;
    }
    
}
