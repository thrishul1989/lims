package com.todaysoft.lims.report.entity.finance;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_INVOICE_INFO")
public class InvoiceInfo extends UuidKeyEntity
{
    /**
     * 
     */
    private static final long serialVersionUID = -5325764759732783882L;
    
    private String drawerId;//开票人ID
    
    private String drawerName;
    
    private String invoiceTitle;
    
    private Date invoiceTime;//开票时间
    
    private String invoicerNo;//发票号
    
    private String receiverName;//领取人
    
    private FinanceInvoiceTask invoiceTask;//开票拆分任务
    
    private String orderIds;
    
    private String orderCodes;
    
    private BigDecimal invoiceAmount;
    
    @Column(name = "DRAWER_ID")
    public String getDrawerId()
    {
        return drawerId;
    }
    
    public void setDrawerId(String drawerId)
    {
        this.drawerId = drawerId;
    }
    
    @Transient
    public String getDrawerName()
    {
        return drawerName;
    }
    
    public void setDrawerName(String drawerName)
    {
        this.drawerName = drawerName;
    }
    
    @Transient
    public String getInvoiceTitle()
    {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle)
    {
        this.invoiceTitle = invoiceTitle;
    }

    @Column(name = "INVOICE_TIME")
    public Date getInvoiceTime()
    {
        return invoiceTime;
    }
    
    public void setInvoiceTime(Date invoiceTime)
    {
        this.invoiceTime = invoiceTime;
    }
    
    @Column(name = "DRAWER_NO")
    public String getInvoicerNo()
    {
        return invoicerNo;
    }
    
    public void setInvoicerNo(String invoicerNo)
    {
        this.invoicerNo = invoicerNo;
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
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INVOICE_TASK_ID")
    @JsonIgnore
    public FinanceInvoiceTask getInvoiceTask()
    {
        return invoiceTask;
    }
    
    public void setInvoiceTask(FinanceInvoiceTask invoiceTask)
    {
        this.invoiceTask = invoiceTask;
    }
    
    @Column(name = "ORDER_IDS")
    public String getOrderIds()
    {
        return orderIds;
    }
    
    public void setOrderIds(String orderIds)
    {
        this.orderIds = orderIds;
    }
    
    @Transient
    public String getOrderCodes()
    {
        return orderCodes;
    }
    
    public void setOrderCodes(String orderCodes)
    {
        this.orderCodes = orderCodes;
    }
    @Column(name = "INVOICE_AMOUNT")
    public BigDecimal getInvoiceAmount()
    {
        return invoiceAmount;
    }
    
    public void setInvoiceAmount(BigDecimal invoiceAmount)
    {
        this.invoiceAmount = invoiceAmount;
    }
}
