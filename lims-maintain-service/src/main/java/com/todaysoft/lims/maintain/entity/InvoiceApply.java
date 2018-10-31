package com.todaysoft.lims.maintain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_INVOICE_APPLY")
public class InvoiceApply extends UuidKeyEntity
{
    private static final long serialVersionUID = 2980378389937764803L;
    
    private String orderIds;
    
    private String recipientName;
    
    private String recipientPhone;
    
    private String recipientAddressDetail;//收件人详细地址
    
    private Integer status;
    
    private String creatorId;//申请人id
    
    private String invoiceTitle;//发票抬头
    
    private String invoiceMethod;
    
    private String contractId;
    
    @Column(name = "ORDER_IDS")
    public String getOrderIds()
    {
        return orderIds;
    }
    
    public void setOrderIds(String orderIds)
    {
        this.orderIds = orderIds;
    }
    
    @Column(name = "RECIPIENT_NAME")
    public String getRecipientName()
    {
        return recipientName;
    }
    
    public void setRecipientName(String recipientName)
    {
        this.recipientName = recipientName;
    }
    
    @Column(name = "RECIPIENT_PHONE")
    public String getRecipientPhone()
    {
        return recipientPhone;
    }
    
    public void setRecipientPhone(String recipientPhone)
    {
        this.recipientPhone = recipientPhone;
    }
    
    @Column(name = "STATUS")
    public Integer getStatus()
    {
        return status;
    }
    
    public void setStatus(Integer status)
    {
        this.status = status;
    }
    @Column(name = "CREATOR_ID")
    public String getCreatorId()
    {
        return creatorId;
    }
    
    public void setCreatorId(String creatorId)
    {
        this.creatorId = creatorId;
    }
    
    @Column(name = "RECIPIENT_ADDRESS_DETAIL")
    public String getRecipientAddressDetail()
    {
        return recipientAddressDetail;
    }
    
    public void setRecipientAddressDetail(String recipientAddressDetail)
    {
        this.recipientAddressDetail = recipientAddressDetail;
    }
    
    @Column(name = "INVOICE_TITLE")
    public String getInvoiceTitle()
    {
        return invoiceTitle;
    }
    
    public void setInvoiceTitle(String invoiceTitle)
    {
        this.invoiceTitle = invoiceTitle;
    }
    
    @Column(name = "INVOICE_METHOD")
    public String getInvoiceMethod()
    {
        return invoiceMethod;
    }
    
    public void setInvoiceMethod(String invoiceMethod)
    {
        this.invoiceMethod = invoiceMethod;
    }
    
    @Column(name = "CONTRACT_ID")
    public String getContractId()
    {
        return contractId;
    }
    
    public void setContractId(String contractId)
    {
        this.contractId = contractId;
    }
}
