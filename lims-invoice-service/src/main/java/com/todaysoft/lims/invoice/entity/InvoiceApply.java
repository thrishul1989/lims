package com.todaysoft.lims.invoice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "LIMS_INVOICE_APPLY")
public class InvoiceApply  implements Serializable
{
    private static final long serialVersionUID = 5142833786826202224L;

    private String id;

    private String code;
    
    private String orderIds;
    
    private String recipientName;
    
    private String recipientPhone;
    
    private String recipientAddressDetail;//收件人详细地址
    
    private Integer status;
    
    private String creatorId;//申请人id
    
    private String invoiceTitle;//发票抬头
    
    private String invoiceMethod;
    
    private String contractId;

    private String dutyParagraph; // 税号

    private String mailbox; // 邮箱


    private String invoiceType;//发票类别;

    private String openingBank;//开户银行

    private String accountNumber;//账号

    private String contactPhone;

    private String companyAddressDetail;//地址（省市区）

    private String taxNo;

    private String invoiceContent;


    @Id
    @Column(name = "ID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "INVOICE_CONTENT")
    public String getInvoiceContent() {
        return invoiceContent;
    }

    public void setInvoiceContent(String invoiceContent) {
        this.invoiceContent = invoiceContent;
    }

    @Column(name = "CODE")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    @Column(name = "TAX_NO")
    public String getTaxNo() {
        return taxNo;
    }

    public void setTaxNo(String taxNo) {
        this.taxNo = taxNo;
    }

    @Column(name = "OPENING_BANK")
    public String getOpeningBank()
    {
        return openingBank;
    }

    public void setOpeningBank(String openingBank)
    {
        this.openingBank = openingBank;
    }

    @Column(name = "ACCOUNT_NUMBER")
    public String getAccountNumber()
    {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber)
    {
        this.accountNumber = accountNumber;
    }

    @Column(name = "CONTACT_PHONE")
    public String getContactPhone()
    {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone)
    {
        this.contactPhone = contactPhone;
    }

    @Column(name = "COMPANY_ADDRESS_DETAIL")
    public String getCompanyAddressDetail()
    {
        return companyAddressDetail;
    }

    public void setCompanyAddressDetail(String companyAddressDetail)
    {
        this.companyAddressDetail = companyAddressDetail;
    }

    @Column(name = "INVOICE_TYPE")
    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
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
