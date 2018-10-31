package com.todaysoft.lims.system.modules.bmm.model.request;

import java.util.Set;

public class DefaultInvoiceSearcher
{
    private String testingType;//营销中心
    
    private String creatorName;//申请人
    
    private String ownerName;//客户
    
    private String invoiceCompany;//送检单位
    
    private String invoiceNo;//发票号
    
    private int pageNo;
    
    private int pageSize;
    
    private String code;
    
    private Integer solveStatus;
    
    private String institution;//开票机构
    
    private String contractCode;
    
    private String isFullPay;

    private String keys;

    private Set<String> ids;
    
    public String getIsFullPay()
    {
        return isFullPay;
    }
    
    public void setIsFullPay(String isFullPay)
    {
        this.isFullPay = isFullPay;
    }
    
    public String getInvoiceNo()
    {
        return invoiceNo;
    }
    
    public void setInvoiceNo(String invoiceNo)
    {
        this.invoiceNo = invoiceNo;
    }
    
    public String getInvoiceCompany()
    {
        return invoiceCompany;
    }
    
    public void setInvoiceCompany(String invoiceCompany)
    {
        this.invoiceCompany = invoiceCompany;
    }
    
    public String getTestingType()
    {
        return testingType;
    }
    
    public void setTestingType(String testingType)
    {
        this.testingType = testingType;
    }
    
    public String getCreatorName()
    {
        return creatorName;
    }
    
    public void setCreatorName(String creatorName)
    {
        this.creatorName = creatorName;
    }
    
    public String getOwnerName()
    {
        return ownerName;
    }
    
    public void setOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
    }
    
    public int getPageNo()
    {
        return pageNo;
    }
    
    public void setPageNo(int pageNo)
    {
        this.pageNo = pageNo;
    }
    
    public int getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public Integer getSolveStatus()
    {
        return solveStatus;
    }
    
    public void setSolveStatus(Integer solveStatus)
    {
        this.solveStatus = solveStatus;
    }
    
    public String getInstitution()
    {
        return institution;
    }
    
    public void setInstitution(String institution)
    {
        this.institution = institution;
    }
    
    public String getContractCode()
    {
        return contractCode;
    }
    
    public void setContractCode(String contractCode)
    {
        this.contractCode = contractCode;
    }

    public String getKeys() {
        return keys;
    }

    public void setKeys(String keys) {
        this.keys = keys;
    }

    public Set<String> getIds() {
        return ids;
    }

    public void setIds(Set<String> ids) {
        this.ids = ids;
    }
}
