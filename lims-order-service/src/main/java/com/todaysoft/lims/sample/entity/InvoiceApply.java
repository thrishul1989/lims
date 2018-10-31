package com.todaysoft.lims.sample.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.todaysoft.lims.persist.UuidKeyEntity;
import com.todaysoft.lims.sample.entity.order.Order;

@Entity
@Table(name = "LIMS_INVOICE_APPLY")
public class InvoiceApply extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 2980378389937764803L;
    
    private String customerId;//客户ID
    
    private String orderIds;//订单ID（多个以逗号分隔）
    
    private BigDecimal invoiceAmount;//开票金额
    
    private Integer status;//状态：1-审核中，2-未通过，3-已开票，4-已寄送
    
    private String invoiceContent;//开票内容
    
    private String invoiceTitle;//发票抬头
    
    private String invoiceType;//发票类别
    
    private String companyName;//单位名称
    
    private String taxNo;//税号
    
    private String openingBank;//开户银行
    
    private String accountNumber;//账号
    
    private String contactName;//联系人
    
    private String contactPhone;
    
    private String companyAddress;//地址（省市区）
    
    private String companyAddressDetail;//详细地址
    
    private String receiverName;//领取人
    
    private String transportType;//运输方式（字典：人工、快递）
    
    private String transportName;//运输人姓名
    
    private String transportPhone;
    
    private String courierNumber;//运输单号
    
    private String recipientName;//收件人姓名
    
    private String recipientPhone;
    
    private String recipientAddress;//收件人地址（省市区）
    
    private String recipientAddressDetail;//收件人详细地址
    
    private Date applyTime;//申请时间
    
    private String creatorId;//申请人id
    
    private String deptName; //申请人部门
    
    private String auditorName;//审核人姓名
    
    private Date auditTime;//审核时间
    
    private String auditorId;
    
    private String authIdea;//审核意见
    
    private String drawerId;//开票人ID
    
    private String drawerName;
    
    private Date invoiceTime;//开票时间
    
    private String invoicerNo;//发票号
    
    private Date sendDate;//寄送时间
    
    private String sendContent;//寄件内容
    
    private String applyResultValue;//审核结果
    
    private String testingType;//营销中心
    
    private String code;
    
    private List<Order> orderList;
    
    private String invoiceMethod;
    
    private String contractId;
    
    private String remark;

    private Integer headerType;

    private String dutyParagraph;

    private String mailbox;
    
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
    
    @Column(name = "REMARK")
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    @Column(name = "CUSTOMER_ID")
    public String getCustomerId()
    {
        return customerId;
    }
    
    public void setCustomerId(String customerId)
    {
        this.customerId = customerId;
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
    
    @Column(name = "INVOICE_AMOUNT")
    public BigDecimal getInvoiceAmount()
    {
        return invoiceAmount;
    }
    
    public void setInvoiceAmount(BigDecimal invoiceAmount)
    {
        this.invoiceAmount = invoiceAmount;
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
    
    @Column(name = "INVOICE_CONTENT")
    public String getInvoiceContent()
    {
        return invoiceContent;
    }
    
    public void setInvoiceContent(String invoiceContent)
    {
        this.invoiceContent = invoiceContent;
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
    
    @Column(name = "INVOICE_TYPE")
    public String getInvoiceType()
    {
        return invoiceType;
    }
    
    public void setInvoiceType(String invoiceType)
    {
        this.invoiceType = invoiceType;
    }
    
    @Column(name = "COMPANY_NAME")
    public String getCompanyName()
    {
        return companyName;
    }
    
    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }
    
    @Column(name = "TAX_NO")
    public String getTaxNo()
    {
        return taxNo;
    }
    
    public void setTaxNo(String taxNo)
    {
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
    
    @Column(name = "CONTACT_NAME")
    public String getContactName()
    {
        return contactName;
    }
    
    public void setContactName(String contactName)
    {
        this.contactName = contactName;
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
    
    @Column(name = "COMPANY_ADDRESS")
    public String getCompanyAddress()
    {
        return companyAddress;
    }
    
    public void setCompanyAddress(String companyAddress)
    {
        this.companyAddress = companyAddress;
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
    
    @Column(name = "RECEIVER_NAME")
    public String getReceiverName()
    {
        return receiverName;
    }
    
    public void setReceiverName(String receiverName)
    {
        this.receiverName = receiverName;
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
    
    @Column(name = "COURIER_NUMBER")
    public String getCourierNumber()
    {
        return courierNumber;
    }
    
    public void setCourierNumber(String courierNumber)
    {
        this.courierNumber = courierNumber;
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
    
    @Column(name = "RECIPIENT_ADDRESS_DETAIL")
    public String getRecipientAddressDetail()
    {
        return recipientAddressDetail;
    }
    
    public void setRecipientAddressDetail(String recipientAddressDetail)
    {
        this.recipientAddressDetail = recipientAddressDetail;
    }
    
    @Column(name = "APPLY_TIME")
    public Date getApplyTime()
    {
        return applyTime;
    }
    
    public void setApplyTime(Date applyTime)
    {
        this.applyTime = applyTime;
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
    
    @Column(name = "AUDITOR_NAME")
    public String getAuditorName()
    {
        return auditorName;
    }
    
    public void setAuditorName(String auditorName)
    {
        this.auditorName = auditorName;
    }
    
    @Column(name = "AUDIT_TIME")
    public Date getAuditTime()
    {
        return auditTime;
    }
    
    public void setAuditTime(Date auditTime)
    {
        this.auditTime = auditTime;
    }
    
    @Column(name = "AUDITOR_ID")
    public String getAuditorId()
    {
        return auditorId;
    }
    
    public void setAuditorId(String auditorId)
    {
        this.auditorId = auditorId;
    }
    
    @Column(name = "AUTH_IDEA")
    public String getAuthIdea()
    {
        return authIdea;
    }
    
    public void setAuthIdea(String authIdea)
    {
        this.authIdea = authIdea;
    }
    
    @Column(name = "DRAWER_ID")
    public String getDrawerId()
    {
        return drawerId;
    }
    
    public void setDrawerId(String drawerId)
    {
        this.drawerId = drawerId;
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
    public Date getInvoiceTime()
    {
        return invoiceTime;
    }
    
    public void setInvoiceTime(Date invoiceTime)
    {
        this.invoiceTime = invoiceTime;
    }
    
    @Column(name = "INVOICER_NO")
    public String getInvoicerNo()
    {
        return invoicerNo;
    }
    
    public void setInvoicerNo(String invoicerNo)
    {
        this.invoicerNo = invoicerNo;
    }
    
    @Column(name = "SEND_DATE")
    public Date getSendDate()
    {
        return sendDate;
    }
    
    public void setSendDate(Date sendDate)
    {
        this.sendDate = sendDate;
    }
    
    @Column(name = "SEND_CONTENT")
    public String getSendContent()
    {
        return sendContent;
    }
    
    public void setSendContent(String sendContent)
    {
        this.sendContent = sendContent;
    }
    
    @Transient
    public String getApplyResultValue()
    {
        return applyResultValue;
    }
    
    public void setApplyResultValue(String applyResultValue)
    {
        this.applyResultValue = applyResultValue;
    }
    
    @Transient
    public String getTestingType()
    {
        return testingType;
    }
    
    public void setTestingType(String testingType)
    {
        this.testingType = testingType;
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
    
    @Transient
    public List<Order> getOrderList()
    {
        return orderList;
    }
    
    public void setOrderList(List<Order> orderList)
    {
        this.orderList = orderList;
    }
    
    @Transient
    public String getDeptName()
    {
        return deptName;
    }
    
    public void setDeptName(String deptName)
    {
        this.deptName = deptName;
    }

    @Column(name = "HEADER_TYPE")
    public Integer getHeaderType() {
        return headerType;
    }

    public void setHeaderType(Integer headerType) {
        this.headerType = headerType;
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
}
