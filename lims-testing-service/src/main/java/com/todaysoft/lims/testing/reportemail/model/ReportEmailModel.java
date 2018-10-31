package com.todaysoft.lims.testing.reportemail.model;

import java.util.Date;
import java.util.List;

public class ReportEmailModel
{
    private String id;
    
    private String orderId;
    
    private String orderCode;
    
    private String productId;
    
    private String productCode;
    
    private Date createTime;
    
    private String status;//1待分配 2 已分配 3 已寄送
    
    private String assignId;
    
    private String assignName;
    
    private Date assignTime;
    
    private String assignedId;
    
    private String emailTime;
    
    private String emailNo;
    
    private String emailType;//快递类型快递类型 0 人工物流 1.。2.。。
    
    private String emailName;
    
    private String emailPhone;
    
    private String emailContent;
    
    private String reportNo;
    
    private Integer issueInvoice;
    
    private Integer invoiceApplyType;
    
    private String invoiceNo;//发票号
    
    private Integer productStatus;//产品状态，0：待送测；1：待数据分析；2：待验证；3：待出报告；4：待审核报告；5：待寄送报告；6：已完成；
    
    private Date reportTime;
    
    private String receivedName;//冗余字段
    
    private String receivedPhone;//冗余
    
    private String address;//冗余
    
    private String reportEmailIds;//冗余请求字段
    
    private Integer reportStatus;//1-待一审，2-待二审，3-待寄送，4-已寄送 5-不寄送

    private String orderExaminee;//受检人

    private String sampleCodes;//样本编号

    private String emailDetail;

    private String projectManager;//项目管理人

    public String getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }


    public ReportEmailModel() {
    }

    public String getOrderExaminee() {
        return orderExaminee;
    }

    public void setOrderExaminee(String orderExaminee) {
        this.orderExaminee = orderExaminee;
    }

    public Integer getReportStatus()
    {
        return reportStatus;
    }
    
    public void setReportStatus(Integer reportStatus)
    {
        this.reportStatus = reportStatus;
    }
    
    public String getReportEmailIds()
    {
        return reportEmailIds;
    }
    
    public void setReportEmailIds(String reportEmailIds)
    {
        this.reportEmailIds = reportEmailIds;
    }
    
    public String getInvoiceNo()
    {
        return invoiceNo;
    }
    
    public void setInvoiceNo(String invoiceNo)
    {
        this.invoiceNo = invoiceNo;
    }
    
    public String getReceivedName()
    {
        return receivedName;
    }
    
    public void setReceivedName(String receivedName)
    {
        this.receivedName = receivedName;
    }
    
    public String getReceivedPhone()
    {
        return receivedPhone;
    }
    
    public void setReceivedPhone(String receivedPhone)
    {
        this.receivedPhone = receivedPhone;
    }
    
    public String getAddress()
    {
        return address;
    }
    
    public void setAddress(String address)
    {
        this.address = address;
    }
    
    public Date getReportTime()
    {
        return reportTime;
    }
    
    public void setReportTime(Date reportTime)
    {
        this.reportTime = reportTime;
    }
    
    public Integer getProductStatus()
    {
        return productStatus;
    }
    
    public void setProductStatus(Integer productStatus)
    {
        this.productStatus = productStatus;
    }
    
    public Integer getIssueInvoice()
    {
        return issueInvoice;
    }
    
    public void setIssueInvoice(Integer issueInvoice)
    {
        this.issueInvoice = issueInvoice;
    }
    
    public Integer getInvoiceApplyType()
    {
        return invoiceApplyType;
    }
    
    public void setInvoiceApplyType(Integer invoiceApplyType)
    {
        this.invoiceApplyType = invoiceApplyType;
    }
    
    public String getReportNo()
    {
        return reportNo;
    }
    
    public void setReportNo(String reportNo)
    {
        this.reportNo = reportNo;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
    
    public String getOrderCode()
    {
        return orderCode;
    }
    
    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }
    
    public String getProductId()
    {
        return productId;
    }
    
    public void setProductId(String productId)
    {
        this.productId = productId;
    }
    
    public String getProductCode()
    {
        return productCode;
    }
    
    public void setProductCode(String productCode)
    {
        this.productCode = productCode;
    }
    
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    public String getStatus()
    {
        return status;
    }
    
    public void setStatus(String status)
    {
        this.status = status;
    }
    
    public String getAssignId()
    {
        return assignId;
    }
    
    public void setAssignId(String assignId)
    {
        this.assignId = assignId;
    }
    
    public String getAssignName()
    {
        return assignName;
    }
    
    public void setAssignName(String assignName)
    {
        this.assignName = assignName;
    }
    
    public Date getAssignTime()
    {
        return assignTime;
    }
    
    public void setAssignTime(Date assignTime)
    {
        this.assignTime = assignTime;
    }
    
    public String getAssignedId()
    {
        return assignedId;
    }
    
    public void setAssignedId(String assignedId)
    {
        this.assignedId = assignedId;
    }
    
    public String getEmailTime()
    {
        return emailTime;
    }
    
    public void setEmailTime(String emailTime)
    {
        this.emailTime = emailTime;
    }
    
    public String getEmailNo()
    {
        return emailNo;
    }
    
    public void setEmailNo(String emailNo)
    {
        this.emailNo = emailNo;
    }
    
    public String getEmailType()
    {
        return emailType;
    }
    
    public void setEmailType(String emailType)
    {
        this.emailType = emailType;
    }
    
    public String getEmailName()
    {
        return emailName;
    }
    
    public void setEmailName(String emailName)
    {
        this.emailName = emailName;
    }
    
    public String getEmailPhone()
    {
        return emailPhone;
    }
    
    public void setEmailPhone(String emailPhone)
    {
        this.emailPhone = emailPhone;
    }
    
    public String getEmailContent()
    {
        return emailContent;
    }
    
    public void setEmailContent(String emailContent)
    {
        this.emailContent = emailContent;
    }

    public String getSampleCodes() {
        return sampleCodes;
    }

    public void setSampleCodes(String sampleCodes) {
        this.sampleCodes = sampleCodes;
    }

    public String getEmailDetail() {
        return emailDetail;
    }

    public void setEmailDetail(String emailDetail) {
        this.emailDetail = emailDetail;
    }
}
