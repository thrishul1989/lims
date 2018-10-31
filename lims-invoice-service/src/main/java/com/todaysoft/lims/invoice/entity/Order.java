package com.todaysoft.lims.invoice.entity;

import javax.persistence.*;

import com.todaysoft.lims.persist.UuidKeyEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "LIMS_ORDER")
public class Order extends UuidKeyEntity
{
    private static final long serialVersionUID = 6894035634140793583L;
    
    public static final Integer INVOICE_APPLY_AUTO = 1;
    
    public static final Integer INVOICE_APPLY_MANUAL = 2;
    
    public static final Integer INVOICE_UNAPPLIED = 1;
    
    public static final Integer INVOICE_INVOICING = 2;
    
    public static final Integer SCHEDULE_STARTED = 2;
    
    private String code;
    
    private String contractId;
    
    private String invoiceTitle;
    
    private Integer invoiceApplyType;
    
    private Integer invoiceApplyStatus;
    
    private Integer scheduleStatus;
    
    private Integer amount;
    
    private Integer subsidiarySampleAmount;
    
    private Integer incomingAmount;
    
    private Integer discountAmount;
    
    private Integer reduceAmount;
    
    private String recipientName;
    
    private String recipientPhone;
    
    private String recipientAddress;
    
    private boolean deleted;

    private String creatorID ;
    
    private String orderType;//订单类型（临检、科研、健康）

    private Integer belongArea; //南北区

    private Integer billingType; // 开票形式:0-不需要;1-电子;2-纸质

    private String dutyParagraph; // 税号

    private String mailbox; // 邮箱

    private List<OrderProduct> orderProductList = new ArrayList<OrderProduct>();

    @Column(name = "BILLING_TYPE")
    public Integer getBillingType() {
        return billingType;
    }

    public void setBillingType(Integer billingType) {
        this.billingType = billingType;
    }

    @Column(name = "DUTY_PARAGRAPH")
    public String getDutyParagraph() {
        return dutyParagraph;
    }

    public void setDutyParagraph(String dutyParagraph) {
        this.dutyParagraph = dutyParagraph;
    }

    @Column(name = "MAILBOX")
    public String getMailbox() {
        return mailbox;
    }

    public void setMailbox(String mailbox) {
        this.mailbox = mailbox;
    }

    @Column(name = "BELONG_AREA")
    public Integer getBelongArea() {
        return belongArea;
    }

    public void setBelongArea(Integer belongArea) {
        this.belongArea = belongArea;
    }

    @Column(name = "CODE")
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
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
    
    @Column(name = "INVOICE_TITLE")
    public String getInvoiceTitle()
    {
        return invoiceTitle;
    }
    
    public void setInvoiceTitle(String invoiceTitle)
    {
        this.invoiceTitle = invoiceTitle;
    }
    
    @Column(name = "INVOICE_APPLY_TYPE")
    public Integer getInvoiceApplyType()
    {
        return invoiceApplyType;
    }
    
    public void setInvoiceApplyType(Integer invoiceApplyType)
    {
        this.invoiceApplyType = invoiceApplyType;
    }
    
    @Column(name = "ISSUE_INVOICE")
    public Integer getInvoiceApplyStatus()
    {
        return invoiceApplyStatus;
    }
    
    public void setInvoiceApplyStatus(Integer invoiceApplyStatus)
    {
        this.invoiceApplyStatus = invoiceApplyStatus;
    }
    
    @Column(name = "SHEDULE_STATUS")
    public Integer getScheduleStatus()
    {
        return scheduleStatus;
    }
    
    public void setScheduleStatus(Integer scheduleStatus)
    {
        this.scheduleStatus = scheduleStatus;
    }
    
    @Column(name = "AMOUNT")
    public Integer getAmount()
    {
        return amount;
    }
    
    public void setAmount(Integer amount)
    {
        this.amount = amount;
    }
    
    @Column(name = "SUBSIDIARY_SAMPLE_AMOUNT")
    public Integer getSubsidiarySampleAmount()
    {
        return subsidiarySampleAmount;
    }
    
    public void setSubsidiarySampleAmount(Integer subsidiarySampleAmount)
    {
        this.subsidiarySampleAmount = subsidiarySampleAmount;
    }
    
    @Column(name = "INCOMING_AMOUNT")
    public Integer getIncomingAmount()
    {
        return incomingAmount;
    }
    
    public void setIncomingAmount(Integer incomingAmount)
    {
        this.incomingAmount = incomingAmount;
    }
    
    @Column(name = "DISCOUNT_AMOUNT")
    public Integer getDiscountAmount()
    {
        return discountAmount;
    }
    
    public void setDiscountAmount(Integer discountAmount)
    {
        this.discountAmount = discountAmount;
    }
    
    @Column(name = "REDUCE_AMOUNT")
    public Integer getReduceAmount()
    {
        return reduceAmount;
    }
    
    public void setReduceAmount(Integer reduceAmount)
    {
        this.reduceAmount = reduceAmount;
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
    
    @Column(name = "RECIPIENT_ADDRESS")
    public String getRecipientAddress()
    {
        return recipientAddress;
    }
    
    public void setRecipientAddress(String recipientAddress)
    {
        this.recipientAddress = recipientAddress;
    }
    
    @Column(name = "DEL_FLAG")
    public boolean isDeleted()
    {
        return deleted;
    }
    
    public void setDeleted(boolean deleted)
    {
        this.deleted = deleted;
    }

    @Column(name = "ORDER_TYPE")
    public String getOrderType()
    {
        return orderType;
    }
    
    public void setOrderType(String orderType)
    {
        this.orderType = orderType;
    }

    @Column(name = "CREATOR_ID")
    public String getCreatorID() {
        return creatorID;
    }

    public void setCreatorID(String creatorID) {
        this.creatorID = creatorID;
    }

    @OneToMany(mappedBy = "order", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    public List<OrderProduct> getOrderProductList()
    {
        return orderProductList;
    }

    public void setOrderProductList(List<OrderProduct> orderProductList)
    {
        this.orderProductList = orderProductList;
    }
}