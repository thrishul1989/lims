package com.todaysoft.lims.system.modules.bmm.model;

import java.math.BigDecimal;
import java.util.Date;

public class InvoiceInfo
{
    private String id;
    
    private String orderIds;
    
    private String orderCodes;
    
    private String drawerId;//开票人ID
    
    private String drawerName;
    
    private Date invoiceTime;//开票时间
    
    private String invoicerNo;//发票号
    
    private String receiverName;//领取人
    
    private BigDecimal invoiceAmount;
    
    private String invoiceTitle;

    private String invoiceStatus;

    
    //临床增加
    
    private String institution;
    
    public String getInstitution()
    {
        return institution;
    }
    
    public void setInstitution(String institution)
    {
        this.institution = institution;
    }
    
    public InvoiceInfo()
    {
        
    }
    
    public InvoiceInfo(String drawerId, Date invoiceTime, String invoicerNo, String receiverName, BigDecimal invoiceAmount)
    {
        super();
        this.drawerId = drawerId;
        this.invoiceTime = invoiceTime;
        this.invoicerNo = invoicerNo;
        this.receiverName = receiverName;
        this.invoiceAmount = invoiceAmount;
    }
    
    public InvoiceInfo(String drawerId, Date invoiceTime, String invoicerNo, String receiverName, BigDecimal invoiceAmount, String orderIds)
    {
        super();
        this.drawerId = drawerId;
        this.invoiceTime = invoiceTime;
        this.invoicerNo = invoicerNo;
        this.receiverName = receiverName;
        this.invoiceAmount = invoiceAmount;
        this.orderIds = orderIds;
    }

    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(String invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getOrderIds()
    {
        return orderIds;
    }
    
    public void setOrderIds(String orderIds)
    {
        this.orderIds = orderIds;
    }
    
    public String getOrderCodes()
    {
        return orderCodes;
    }
    
    public void setOrderCodes(String orderCodes)
    {
        this.orderCodes = orderCodes;
    }
    
    public String getDrawerId()
    {
        return drawerId;
    }
    
    public void setDrawerId(String drawerId)
    {
        this.drawerId = drawerId;
    }
    
    public String getDrawerName()
    {
        return drawerName;
    }
    
    public void setDrawerName(String drawerName)
    {
        this.drawerName = drawerName;
    }
    
    public Date getInvoiceTime()
    {
        return invoiceTime;
    }
    
    public void setInvoiceTime(Date invoiceTime)
    {
        this.invoiceTime = invoiceTime;
    }
    
    public String getInvoicerNo()
    {
        return invoicerNo;
    }
    
    public void setInvoicerNo(String invoicerNo)
    {
        this.invoicerNo = invoicerNo;
    }
    
    public String getReceiverName()
    {
        return receiverName;
    }
    
    public void setReceiverName(String receiverName)
    {
        this.receiverName = receiverName;
    }
    
    public BigDecimal getInvoiceAmount()
    {
        return invoiceAmount;
    }
    
    public void setInvoiceAmount(BigDecimal invoiceAmount)
    {
        this.invoiceAmount = invoiceAmount;
    }
    
    public String getInvoiceTitle()
    {
        return invoiceTitle;
    }
    
    public void setInvoiceTitle(String invoiceTitle)
    {
        this.invoiceTitle = invoiceTitle;
    }
    
}
