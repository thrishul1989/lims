package com.todaysoft.lims.system.model.vo.order.orderReportForm;

import java.util.Date;

public class BillingInfoModel
{
  //-----------开票信息--------------
    private String drawerNo;//开票单号
    private String institution;//开票单位
    private double invoiceAmount;//开票金额
    private String drawerName;//开票人
    private String invoiceTime;//开票时间
    private String receiverName;//领取人
    private String transportStatus;//寄送状态
    private String trackType;//快递类别
    private String trackNumber;//快递单号
    private String transportName;//寄送人
    private String transportTime;//寄送时间
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
    public double getInvoiceAmount()
    {
        return invoiceAmount;
    }
    public void setInvoiceAmount(double invoiceAmount)
    {
        this.invoiceAmount = invoiceAmount;
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
    public String getTransportStatus()
    {
        return transportStatus;
    }
    public void setTransportStatus(String transportStatus)
    {
        this.transportStatus = transportStatus;
    }
    public String getTrackType()
    {
        return trackType;
    }
    public void setTrackType(String trackType)
    {
        this.trackType = trackType;
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
    public String getTransportTime()
    {
        return transportTime;
    }
    public void setTransportTime(String transportTime)
    {
        this.transportTime = transportTime;
    }
    
}
