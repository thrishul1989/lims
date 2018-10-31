package com.todaysoft.lims.system.modules.bmm.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.todaysoft.lims.system.model.vo.contract.Contract;
import com.todaysoft.lims.system.model.vo.contract.ContractContent;
import com.todaysoft.lims.system.model.vo.contract.ContractPartyB;
import com.todaysoft.lims.system.model.vo.order.Order;

public class InvoiceApply
{
    private String id;
    
    private String customerId;//客户ID
    
    private String customerName;//客户姓名
    
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
    
    private String applyResult;
    
    private String testingType;//营销中心
    
    private String code;
    
    private List<Order> orderList;
    
    private String invoiceMethod;
    
    private String contractId;
    
    private String remark;
    
    private ContractContent contractContent;
    
    private ContractPartyB contractPartyB;
    
    private Contract contract;

    private Integer headerType;

    private String dutyParagraph;

    private String mailbox;

    public Integer getHeaderType() {
        return headerType;
    }

    public void setHeaderType(Integer headerType) {
        this.headerType = headerType;
    }

    public String getDutyParagraph() {
        return dutyParagraph;
    }

    public void setDutyParagraph(String dutyParagraph) {
        this.dutyParagraph = dutyParagraph;
    }

    public String getMailbox() {
        return mailbox;
    }

    public void setMailbox(String mailbox) {
        this.mailbox = mailbox;
    }

    public Contract getContract()
    {
        return contract;
    }
    
    public void setContract(Contract contract)
    {
        this.contract = contract;
    }
    
    public ContractPartyB getContractPartyB()
    {
        return contractPartyB;
    }
    
    public void setContractPartyB(ContractPartyB contractPartyB)
    {
        this.contractPartyB = contractPartyB;
    }
    
    public ContractContent getContractContent()
    {
        return contractContent;
    }
    
    public void setContractContent(ContractContent contractContent)
    {
        this.contractContent = contractContent;
    }
    
    public String getInvoiceMethod()
    {
        return invoiceMethod;
    }
    
    public void setInvoiceMethod(String invoiceMethod)
    {
        this.invoiceMethod = invoiceMethod;
    }
    
    public String getContractId()
    {
        return contractId;
    }
    
    public void setContractId(String contractId)
    {
        this.contractId = contractId;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public String getCustomerName()
    {
        return customerName;
    }
    
    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }
    
    public String getDeptName()
    {
        return deptName;
    }
    
    public void setDeptName(String deptName)
    {
        this.deptName = deptName;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getCustomerId()
    {
        return customerId;
    }
    
    public void setCustomerId(String customerId)
    {
        this.customerId = customerId;
    }
    
    public String getOrderIds()
    {
        return orderIds;
    }
    
    public void setOrderIds(String orderIds)
    {
        this.orderIds = orderIds;
    }
    
    public BigDecimal getInvoiceAmount()
    {
        return invoiceAmount;
    }
    
    public void setInvoiceAmount(BigDecimal invoiceAmount)
    {
        this.invoiceAmount = invoiceAmount;
    }
    
    public Integer getStatus()
    {
        return status;
    }
    
    public void setStatus(Integer status)
    {
        this.status = status;
    }
    
    public String getInvoiceContent()
    {
        return invoiceContent;
    }
    
    public void setInvoiceContent(String invoiceContent)
    {
        this.invoiceContent = invoiceContent;
    }
    
    public String getInvoiceTitle()
    {
        return invoiceTitle;
    }
    
    public void setInvoiceTitle(String invoiceTitle)
    {
        this.invoiceTitle = invoiceTitle;
    }
    
    public String getInvoiceType()
    {
        return invoiceType;
    }
    
    public void setInvoiceType(String invoiceType)
    {
        this.invoiceType = invoiceType;
    }
    
    public String getCompanyName()
    {
        return companyName;
    }
    
    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }
    
    public String getTaxNo()
    {
        return taxNo;
    }
    
    public void setTaxNo(String taxNo)
    {
        this.taxNo = taxNo;
    }
    
    public String getOpeningBank()
    {
        return openingBank;
    }
    
    public void setOpeningBank(String openingBank)
    {
        this.openingBank = openingBank;
    }
    
    public String getAccountNumber()
    {
        return accountNumber;
    }
    
    public void setAccountNumber(String accountNumber)
    {
        this.accountNumber = accountNumber;
    }
    
    public String getContactName()
    {
        return contactName;
    }
    
    public void setContactName(String contactName)
    {
        this.contactName = contactName;
    }
    
    public String getContactPhone()
    {
        return contactPhone;
    }
    
    public void setContactPhone(String contactPhone)
    {
        this.contactPhone = contactPhone;
    }
    
    public String getCompanyAddress()
    {
        return companyAddress;
    }
    
    public void setCompanyAddress(String companyAddress)
    {
        this.companyAddress = companyAddress;
    }
    
    public String getCompanyAddressDetail()
    {
        return companyAddressDetail;
    }
    
    public void setCompanyAddressDetail(String companyAddressDetail)
    {
        this.companyAddressDetail = companyAddressDetail;
    }
    
    public String getReceiverName()
    {
        return receiverName;
    }
    
    public void setReceiverName(String receiverName)
    {
        this.receiverName = receiverName;
    }
    
    public String getTransportType()
    {
        return transportType;
    }
    
    public void setTransportType(String transportType)
    {
        this.transportType = transportType;
    }
    
    public String getTransportName()
    {
        return transportName;
    }
    
    public void setTransportName(String transportName)
    {
        this.transportName = transportName;
    }
    
    public String getTransportPhone()
    {
        return transportPhone;
    }
    
    public void setTransportPhone(String transportPhone)
    {
        this.transportPhone = transportPhone;
    }
    
    public String getCourierNumber()
    {
        return courierNumber;
    }
    
    public void setCourierNumber(String courierNumber)
    {
        this.courierNumber = courierNumber;
    }
    
    public String getRecipientName()
    {
        return recipientName;
    }
    
    public void setRecipientName(String recipientName)
    {
        this.recipientName = recipientName;
    }
    
    public String getRecipientPhone()
    {
        return recipientPhone;
    }
    
    public void setRecipientPhone(String recipientPhone)
    {
        this.recipientPhone = recipientPhone;
    }
    
    public String getRecipientAddress()
    {
        return recipientAddress;
    }
    
    public void setRecipientAddress(String recipientAddress)
    {
        this.recipientAddress = recipientAddress;
    }
    
    public String getRecipientAddressDetail()
    {
        return recipientAddressDetail;
    }
    
    public void setRecipientAddressDetail(String recipientAddressDetail)
    {
        this.recipientAddressDetail = recipientAddressDetail;
    }
    
    public Date getApplyTime()
    {
        return applyTime;
    }
    
    public void setApplyTime(Date applyTime)
    {
        this.applyTime = applyTime;
    }
    
    public String getCreatorId()
    {
        return creatorId;
    }
    
    public void setCreatorId(String creatorId)
    {
        this.creatorId = creatorId;
    }
    
    public String getAuditorName()
    {
        return auditorName;
    }
    
    public void setAuditorName(String auditorName)
    {
        this.auditorName = auditorName;
    }
    
    public Date getAuditTime()
    {
        return auditTime;
    }
    
    public void setAuditTime(Date auditTime)
    {
        this.auditTime = auditTime;
    }
    
    public String getAuditorId()
    {
        return auditorId;
    }
    
    public void setAuditorId(String auditorId)
    {
        this.auditorId = auditorId;
    }
    
    public String getAuthIdea()
    {
        return authIdea;
    }
    
    public void setAuthIdea(String authIdea)
    {
        this.authIdea = authIdea;
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
    
    public Date getSendDate()
    {
        return sendDate;
    }
    
    public void setSendDate(Date sendDate)
    {
        this.sendDate = sendDate;
    }
    
    public String getSendContent()
    {
        return sendContent;
    }
    
    public void setSendContent(String sendContent)
    {
        this.sendContent = sendContent;
    }
    
    public String getApplyResultValue()
    {
        return applyResultValue;
    }
    
    public void setApplyResultValue(String applyResultValue)
    {
        this.applyResultValue = applyResultValue;
    }
    
    public String getTestingType()
    {
        return testingType;
    }
    
    public void setTestingType(String testingType)
    {
        this.testingType = testingType;
    }
    
    public String getApplyResult()
    {
        return applyResult;
    }
    
    public void setApplyResult(String applyResult)
    {
        this.applyResult = applyResult;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public List<Order> getOrderList()
    {
        return orderList;
    }
    
    public void setOrderList(List<Order> orderList)
    {
        this.orderList = orderList;
    }
}
