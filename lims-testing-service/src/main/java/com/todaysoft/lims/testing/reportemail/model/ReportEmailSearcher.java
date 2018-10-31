package com.todaysoft.lims.testing.reportemail.model;

import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.persist.IDataAuthoritySearcher;
import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.testing.base.entity.TestingReportEmail;
import com.todaysoft.lims.utils.StringUtils;

public class ReportEmailSearcher extends IDataAuthoritySearcher implements ISearcher<TestingReportEmail>
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
    
    private List<String> orderIds;
    
    private Integer status;

    private Integer mailStatus;//0-不需要邮寄，1-需要

    private String projectManager;//项目管理人

    public String getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
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
    
    public List<String> getOrderIds()
    {
        return orderIds;
    }
    
    public void setOrderIds(List<String> orderIds)
    {
        this.orderIds = orderIds;
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

    public NamedQueryer toQuery(Integer status,String projectManager)
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("FROM TestingReportEmail s WHERE 1=1  and s.order.id in (:orderlist) ");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        paramNames.add("orderlist");
        parameters.add(orderIds);

        NamedQueryer queryer = new NamedQueryer();
        if (!StringUtils.isEmpty(projectManager))
        {
            hql.append(" AND EXISTS (SELECT o.id FROM Order o WHERE o.id = s.order.id AND o.projectManager = :projectManager)");
            paramNames.add("projectManager");
            parameters.add(projectManager);
        }
        if(1 == status || 2== status){
            hql.append(" and s.status = :status"); 
            paramNames.add("status");
            parameters.add(status.toString());
        }
        else if(3 == status){
            hql.append(" and s.status in ('3','4')");
        }
        
        if(1 == status || 2== status){
            hql.append(" order by s.order.submitTime , s.order.id ");  
        }
        else if(3 == status){
            addAuthFilter(hql, paramNames, parameters, "assignedId");
            hql.append(" order by s.updateTime desc "); 
        }
        
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }

    @Override
    public Class<TestingReportEmail> getEntityClass()
    {
        // TODO Auto-generated method stub
        return TestingReportEmail.class;
    }

    @Override
    public NamedQueryer toQuery()
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public NamedQueryer toAuthQuery()
    {
        // TODO Auto-generated method stub
        return null;
    }
    
}
