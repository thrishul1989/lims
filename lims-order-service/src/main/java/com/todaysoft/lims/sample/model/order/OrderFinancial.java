package com.todaysoft.lims.sample.model.order;

import java.util.List;

import com.todaysoft.lims.sample.entity.InvoiceInfo;

public class OrderFinancial
{
    private String code;
    
    private Integer paymentDelayStatus;
    
    private String settlementType;
    
    private String submitTime;
    
    private String ownerName; //订单所属客户
    
    private String ownerCompany;//送检医院
    
    private String productsName;//检测产品
    
    private Integer samplingAmount;//取样费
    
    private String creatorName;//业务员
    
    private String examineeName;
    
    private String payTime;
    
    private Integer amount;
    
    private Integer subsidiarySampleAmount;
    
    private Integer discountAmount;
    
    private Integer reduceAmount;
    
    private Integer incomingAmount;
    
    private String payType; //付款方式
    
    private String posNo;
    
    private String contactName;//联系人
    
    //、、、、、发票类型、、、、、、 开票抬头     开票内容    开票金额    发票号     开票日期    开票人 //
    
    private String invoiceType; //1 普通  2 专用
    
    private String invoiceTitle;
    
    private String invoiceContent;
    
    private Integer invoiceAmount;
    
    private String invoicerNo;
    
    private String invoiceTime;
    
    private String drawerName;
    
    private List<InvoiceInfo> invoiceInfos;
    
    private String testingStatus; //状态
    
    private String paymentStatus; //付款
    
    private String recipientPhone;//收件人电话
    
    private String recipientAddress;//收件人地址
    
    private String transportNo; //快递号
    
    private String paymentRemark;
    
    private String institution;
    
    private String orderType;
    
    public String getOrderType()
    {
        return orderType;
    }
    
    public void setOrderType(String orderType)
    {
        this.orderType = orderType;
    }
    
    public String getTestingStatus()
    {
        return testingStatus;
    }
    
    public void setTestingStatus(String testingStatus)
    {
        this.testingStatus = testingStatus;
    }
    
    public String getPaymentStatus()
    {
        return paymentStatus;
    }
    
    public void setPaymentStatus(String paymentStatus)
    {
        this.paymentStatus = paymentStatus;
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
    
    public String getPaymentRemark()
    {
        return paymentRemark;
    }
    
    public void setPaymentRemark(String paymentRemark)
    {
        this.paymentRemark = paymentRemark;
    }
    
    public String getInstitution()
    {
        return institution;
    }
    
    public void setInstitution(String institution)
    {
        this.institution = institution;
    }
    
    public Integer getPaymentDelayStatus()
    {
        return paymentDelayStatus;
    }
    
    public void setPaymentDelayStatus(Integer paymentDelayStatus)
    {
        this.paymentDelayStatus = paymentDelayStatus;
    }
    
    public List<InvoiceInfo> getInvoiceInfos()
    {
        return invoiceInfos;
    }
    
    public void setInvoiceInfos(List<InvoiceInfo> invoiceInfos)
    {
        this.invoiceInfos = invoiceInfos;
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
    
    public Integer getInvoiceAmount()
    {
        return invoiceAmount;
    }
    
    public void setInvoiceAmount(Integer invoiceAmount)
    {
        this.invoiceAmount = invoiceAmount;
    }
    
    public String getInvoicerNo()
    {
        return invoicerNo;
    }
    
    public void setInvoicerNo(String invoicerNo)
    {
        this.invoicerNo = invoicerNo;
    }
    
    public String getInvoiceTime()
    {
        return invoiceTime;
    }
    
    public void setInvoiceTime(String invoiceTime)
    {
        this.invoiceTime = invoiceTime;
    }
    
    public String getDrawerName()
    {
        return drawerName;
    }
    
    public void setDrawerName(String drawerName)
    {
        this.drawerName = drawerName;
    }
    
    public String getSettlementType()
    {
        return settlementType;
    }
    
    public void setSettlementType(String settlementType)
    {
        this.settlementType = settlementType;
    }
    
    public String getSubmitTime()
    {
        return submitTime;
    }
    
    public void setSubmitTime(String submitTime)
    {
        this.submitTime = submitTime;
    }
    
    public String getOwnerName()
    {
        return ownerName;
    }
    
    public void setOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
    }
    
    public String getOwnerCompany()
    {
        return ownerCompany;
    }
    
    public void setOwnerCompany(String ownerCompany)
    {
        this.ownerCompany = ownerCompany;
    }
    
    public String getProductsName()
    {
        return productsName;
    }
    
    public void setProductsName(String productsName)
    {
        this.productsName = productsName;
    }
    
    public Integer getSamplingAmount()
    {
        return samplingAmount;
    }
    
    public void setSamplingAmount(Integer samplingAmount)
    {
        this.samplingAmount = samplingAmount;
    }
    
    public String getCreatorName()
    {
        return creatorName;
    }
    
    public void setCreatorName(String creatorName)
    {
        this.creatorName = creatorName;
    }
    
    public String getExamineeName()
    {
        return examineeName;
    }
    
    public void setExamineeName(String examineeName)
    {
        this.examineeName = examineeName;
    }
    
    public String getPayTime()
    {
        return payTime;
    }
    
    public void setPayTime(String payTime)
    {
        this.payTime = payTime;
    }
    
    public Integer getAmount()
    {
        return amount;
    }
    
    public void setAmount(Integer amount)
    {
        this.amount = amount;
    }
    
    public Integer getSubsidiarySampleAmount()
    {
        return subsidiarySampleAmount;
    }
    
    public void setSubsidiarySampleAmount(Integer subsidiarySampleAmount)
    {
        this.subsidiarySampleAmount = subsidiarySampleAmount;
    }
    
    public Integer getDiscountAmount()
    {
        return discountAmount;
    }
    
    public void setDiscountAmount(Integer discountAmount)
    {
        this.discountAmount = discountAmount;
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
    
    public String getContactName()
    {
        return contactName;
    }
    
    public void setContactName(String contactName)
    {
        this.contactName = contactName;
    }
    
    public String getInvoiceType()
    {
        return invoiceType;
    }
    
    public void setInvoiceType(String invoiceType)
    {
        this.invoiceType = invoiceType;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
}
