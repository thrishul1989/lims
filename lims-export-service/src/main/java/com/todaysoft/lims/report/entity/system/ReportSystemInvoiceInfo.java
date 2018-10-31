package com.todaysoft.lims.report.entity.system;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "REPORT_SYSTEM_INVOICE_INFO")
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
    
    
    @Column(name = "TASK_ID")
    public String getTaskId()
    {
        return taskId;
    }
    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ID")
    @JsonIgnore
    public ReportSystemOrderInfo getOrderInfo()
    {
        return orderInfo;
    }
    public void setOrderInfo(ReportSystemOrderInfo orderInfo)
    {
        this.orderInfo = orderInfo;
    }
    
    @Column(name = "DRAWER_NO")
    public String getDrawerNo()
    {
        return drawerNo;
    }
    public void setDrawerNo(String drawerNo)
    {
        this.drawerNo = drawerNo;
    }
    
    @Column(name = "INSTITUTION")
    public String getInstitution()
    {
        return institution;
    }
    public void setInstitution(String institution)
    {
        this.institution = institution;
    }
    
    @Column(name = "AMOUNT")
    public String getAmount()
    {
        return amount;
    }
    public void setAmount(String amount)
    {
        this.amount = amount;
    }
    
    @Column(name = "DRAWER_NAME")
    public String getDrawerName()
    {
        return drawerName;
    }
    public void setDrawerName(String drawerName)
    {
        this.drawerName = drawerName;
    }
    
    @Column(name = "INVOICE_TIME")
    public String getInvoiceTime()
    {
        return invoiceTime;
    }
    public void setInvoiceTime(String invoiceTime)
    {
        this.invoiceTime = invoiceTime;
    }
    
    @Column(name = "RECEIVER_NAME")
    public String getReceiverName()
    {
        return receiverName;
    }
    public void setReceiverName(String receiverName)
    {
        this.receiverName = receiverName;
    }
    
    @Column(name = "SEND_STATUS")
    public String getSendStatus()
    {
        return sendStatus;
    }
    public void setSendStatus(String sendStatus)
    {
        this.sendStatus = sendStatus;
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
    
    @Column(name = "OPERATE_NAME")
    public String getOperateName()
    {
        return operateName;
    }
    public void setOperateName(String operateName)
    {
        this.operateName = operateName;
    }
    
    @Column(name = "SEND_TIME")
    public String getSendTime()
    {
        return sendTime;
    }
    public void setSendTime(String sendTime)
    {
        this.sendTime = sendTime;
    }
    
}
