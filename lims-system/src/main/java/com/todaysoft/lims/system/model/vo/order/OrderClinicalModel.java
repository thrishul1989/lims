package com.todaysoft.lims.system.model.vo.order;

import java.util.List;

public class OrderClinicalModel

{
    private String orderId;
    
    private String taskId;
    
    private String orderType;
    
    private String testingStatus;
    
    private String paymentStatus;
    
    private String code; //订单编号
    
    private String examineeName;
    
    private String productNames;
    
    private String companyName;//公司
    
    private String creatorName;//业务员
    
    private Integer incomingAmount;
    
    private String remark;
    
    private String recipientPhone;//收件人电话
    
    private String recipientAddress;//收件人地址
    
    private String invoiceContent;
    
    private List<ReportClinicalOrderInvoice> reportClinicalOrderInvoice;
    
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
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public String getExamineeName()
    {
        return examineeName;
    }
    
    public void setExamineeName(String examineeName)
    {
        this.examineeName = examineeName;
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
    
    public Integer getIncomingAmount()
    {
        return incomingAmount;
    }
    
    public void setIncomingAmount(Integer incomingAmount)
    {
        this.incomingAmount = incomingAmount;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
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
    
    public List<ReportClinicalOrderInvoice> getReportClinicalOrderInvoice()
    {
        return reportClinicalOrderInvoice;
    }
    
    public void setReportClinicalOrderInvoice(List<ReportClinicalOrderInvoice> reportClinicalOrderInvoice)
    {
        this.reportClinicalOrderInvoice = reportClinicalOrderInvoice;
    }
    
    public String getInvoiceContent()
    {
        return invoiceContent;
    }
    
    public void setInvoiceContent(String invoiceContent)
    {
        this.invoiceContent = invoiceContent;
    }
    
}
