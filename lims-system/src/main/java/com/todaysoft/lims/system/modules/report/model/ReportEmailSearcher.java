package com.todaysoft.lims.system.modules.report.model;

import java.util.List;

import com.todaysoft.lims.persist.DataAuthoritySearcher;

public class ReportEmailSearcher
{
    private Integer pageNo;
    
    private Integer pageSize;
    
    private String orderCode;

    private String productCode;
    
    private String reportNo;

    private String orderExaminee;

    private String companyName;

    private String ownerId;

    private String contractCode;

    private String contractName;

    private String testingType;

    private String creatorName;
    
    private Integer issueInvoice;
    
    private Integer invoiceType;
    
    private String address;
    
    private String receivedPhone;
    
    private String assignedId;
    
    private Integer status;

    private Integer mailStatus;
    
    private List<DataAuthoritySearcher> dataAuthoritySearcher;//数据权限

    private String projectManager;//项目管理人

    private String prjManagerName;//项目管理人名称

    public String getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }

    public String getPrjManagerName() {
        return prjManagerName;
    }

    public void setPrjManagerName(String prjManagerName) {
        this.prjManagerName = prjManagerName;
    }

    public Integer getMailStatus() {
        return mailStatus;
    }

    public void setMailStatus(Integer mailStatus) {
        this.mailStatus = mailStatus;
    }

    public Integer getStatus()
    {
        return status;
    }
    
    public void setStatus(Integer status)
    {
        this.status = status;
    }
    
    public Integer getPageNo()
    {
        return pageNo;
    }
    
    public void setPageNo(Integer pageNo)
    {
        this.pageNo = pageNo;
    }
    
    public Integer getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(Integer pageSize)
    {
        this.pageSize = pageSize;
    }
    
    public String getOrderCode()
    {
        return orderCode;
    }
    
    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }
    
    public String getReportNo()
    {
        return reportNo;
    }
    
    public void setReportNo(String reportNo)
    {
        this.reportNo = reportNo;
    }
    
    public String getTestingType()
    {
        return testingType;
    }
    
    public void setTestingType(String testingType)
    {
        this.testingType = testingType;
    }
    
    public Integer getIssueInvoice()
    {
        return issueInvoice;
    }
    
    public void setIssueInvoice(Integer issueInvoice)
    {
        this.issueInvoice = issueInvoice;
    }
    
    public Integer getInvoiceType()
    {
        return invoiceType;
    }
    
    public void setInvoiceType(Integer invoiceType)
    {
        this.invoiceType = invoiceType;
    }
    
    public String getAddress()
    {
        return address;
    }
    
    public void setAddress(String address)
    {
        this.address = address;
    }
    
    public String getReceivedPhone()
    {
        return receivedPhone;
    }
    
    public void setReceivedPhone(String receivedPhone)
    {
        this.receivedPhone = receivedPhone;
    }
    
    public String getAssignedId()
    {
        return assignedId;
    }
    
    public void setAssignedId(String assignedId)
    {
        this.assignedId = assignedId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getOrderExaminee() {
        return orderExaminee;
    }

    public void setOrderExaminee(String orderExaminee) {
        this.orderExaminee = orderExaminee;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<DataAuthoritySearcher> getDataAuthoritySearcher()
    {
        return dataAuthoritySearcher;
    }

    public void setDataAuthoritySearcher(List<DataAuthoritySearcher> dataAuthoritySearcher)
    {
        this.dataAuthoritySearcher = dataAuthoritySearcher;
    }
    
}
