package com.todaysoft.lims.system.model.vo.order;

import java.util.Date;
import java.util.List;

import com.todaysoft.lims.system.modules.bmm.model.InvoiceInfo;

public class OrderFinancialModel
{
    
    private String orderId;
    
    private String taskId;
    
    private String settlementType;
    
    private String code; //订单编号
    
    private Date submitTime;
    
    private String examineeName;
    
    private String ownerName;
    
    private String productNames;
    
    private String companyName;//公司
    
    private String creatorName;//业务员
    
    private Integer samplingAmount; //增加收样费用
    
    private Date checkTime;
    
    private Integer orderAmount;
    
    private Integer needPay;
    
    private Integer reduceAmount; //减免金额
    
    private Integer incomingAmount;
    
    private String payType;
    
    private String posNo;
    
    private String paymenter;
    
    private String invoiceApplyType;
    
    private String invoiceTitle;
    
    private String invoiceContent;
    
    private String drawerNo;
    
    private Integer amount;
    
    private String name;
    
    private Date invoiceTime;
    
    private List<ReportFinancialOrderInvoice> reportFinancialOrderInvoice;
    
    /**********************************************/
    
    private List<InvoiceInfo> InvoiceInfos;
    
    private String institution;
    
    private String recipientPhone;
    
    private String recipientAddress;
    
    private String transportNo;
    
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
    
    public String getTaskId()
    {
        return taskId;
    }
    
    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }
    
    public String getSettlementType()
    {
        return settlementType;
    }
    
    public void setSettlementType(String settlementType)
    {
        this.settlementType = settlementType;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public Date getSubmitTime()
    {
        return submitTime;
    }
    
    public void setSubmitTime(Date submitTime)
    {
        this.submitTime = submitTime;
    }
    
    public String getExamineeName()
    {
        return examineeName;
    }
    
    public void setExamineeName(String examineeName)
    {
        this.examineeName = examineeName;
    }
    
    public String getOwnerName()
    {
        return ownerName;
    }
    
    public void setOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
    }
    
    public String getProductNames()
    {
        return productNames;
    }
    
    public void setProductNames(String productNames)
    {
        this.productNames = productNames;
    }
    
    public String getCompanyName()
    {
        return companyName;
    }
    
    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }
    
    public String getCreatorName()
    {
        return creatorName;
    }
    
    public void setCreatorName(String creatorName)
    {
        this.creatorName = creatorName;
    }
    
    public Integer getSamplingAmount()
    {
        return samplingAmount;
    }
    
    public void setSamplingAmount(Integer samplingAmount)
    {
        this.samplingAmount = samplingAmount;
    }
    
    public Date getCheckTime()
    {
        return checkTime;
    }
    
    public void setCheckTime(Date checkTime)
    {
        this.checkTime = checkTime;
    }
    
    public Integer getOrderAmount()
    {
        return orderAmount;
    }
    
    public void setOrderAmount(Integer orderAmount)
    {
        this.orderAmount = orderAmount;
    }
    
    public Integer getNeedPay()
    {
        return needPay;
    }
    
    public void setNeedPay(Integer needPay)
    {
        this.needPay = needPay;
    }
    
    public Integer getReduceAmount()
    {
        return reduceAmount;
    }
    
    public void setReduceAmount(Integer reduceAmount)
    {
        this.reduceAmount = reduceAmount;
    }
    
    public Integer getIncomingAmount()
    {
        return incomingAmount;
    }
    
    public void setIncomingAmount(Integer incomingAmount)
    {
        this.incomingAmount = incomingAmount;
    }
    
    public String getPayType()
    {
        return payType;
    }
    
    public void setPayType(String payType)
    {
        this.payType = payType;
    }
    
    public String getPosNo()
    {
        return posNo;
    }
    
    public void setPosNo(String posNo)
    {
        this.posNo = posNo;
    }
    
    public String getPaymenter()
    {
        return paymenter;
    }
    
    public void setPaymenter(String paymenter)
    {
        this.paymenter = paymenter;
    }
    
    public String getInvoiceApplyType()
    {
        return invoiceApplyType;
    }
    
    public void setInvoiceApplyType(String invoiceApplyType)
    {
        this.invoiceApplyType = invoiceApplyType;
    }
    
    public String getInvoiceTitle()
    {
        return invoiceTitle;
    }
    
    public void setInvoiceTitle(String invoiceTitle)
    {
        this.invoiceTitle = invoiceTitle;
    }
    
    public String getInvoiceContent()
    {
        return invoiceContent;
    }
    
    public void setInvoiceContent(String invoiceContent)
    {
        this.invoiceContent = invoiceContent;
    }
    
    public String getDrawerNo()
    {
        return drawerNo;
    }
    
    public void setDrawerNo(String drawerNo)
    {
        this.drawerNo = drawerNo;
    }
    
    public Integer getAmount()
    {
        return amount;
    }
    
    public void setAmount(Integer amount)
    {
        this.amount = amount;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public Date getInvoiceTime()
    {
        return invoiceTime;
    }
    
    public void setInvoiceTime(Date invoiceTime)
    {
        this.invoiceTime = invoiceTime;
    }
    
    public List<ReportFinancialOrderInvoice> getReportFinancialOrderInvoice()
    {
        return reportFinancialOrderInvoice;
    }
    
    public void setReportFinancialOrderInvoice(List<ReportFinancialOrderInvoice> reportFinancialOrderInvoice)
    {
        this.reportFinancialOrderInvoice = reportFinancialOrderInvoice;
    }
    
    public List<InvoiceInfo> getInvoiceInfos()
    {
        return InvoiceInfos;
    }
    
    public void setInvoiceInfos(List<InvoiceInfo> invoiceInfos)
    {
        InvoiceInfos = invoiceInfos;
    }
    
    public String getInstitution()
    {
        return institution;
    }
    
    public void setInstitution(String institution)
    {
        this.institution = institution;
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
    
    public String getTransportNo()
    {
        return transportNo;
    }
    
    public void setTransportNo(String transportNo)
    {
        this.transportNo = transportNo;
    }
    
}
