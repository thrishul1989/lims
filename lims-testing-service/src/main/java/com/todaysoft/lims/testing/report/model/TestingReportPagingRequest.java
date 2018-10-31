package com.todaysoft.lims.testing.report.model;

import java.util.Date;

public class TestingReportPagingRequest
{
    
    private Integer pageNo;
    
    private Integer pageSize;
    
    private int status;
    
    private String orderCode;
    
    private String productCode;
    
    private String productName;
    
    private String sampleCode;
    
    private String technicalMan;
    
    private String contractNumber;
    
    private String customer;
    
    private String testUnit;
    
    private String marketingCenter;
    
    private String analType;
    
    private String path;
    
    private String wordPath;
    
    private String id;
    
    private String inspectMan;

    private String startDate;

    private String endDate;
    
    private Integer resubmit;

    private Integer reportState;

    private String productDutyMan;


    public String getProductDutyMan() {
        return productDutyMan;
    }

    public void setProductDutyMan(String productDutyMan) {
        this.productDutyMan = productDutyMan;
    }

    public Integer getReportState() {
        return reportState;
    }

    public void setReportState(Integer reportState) {
        this.reportState = reportState;
    }

    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public String getTechnicalMan()
    {
        return technicalMan;
    }
    
    public void setTechnicalMan(String technicalMan)
    {
        this.technicalMan = technicalMan;
    }
    
    public int getStatus()
    {
        return status;
    }
    
    public void setStatus(int status)
    {
        this.status = status;
    }
    
    public String getOrderCode()
    {
        return orderCode;
    }
    
    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }
    
    public String getProductCode()
    {
        return productCode;
    }
    
    public void setProductCode(String productCode)
    {
        this.productCode = productCode;
    }
    
    public String getProductName()
    {
        return productName;
    }
    
    public void setProductName(String productName)
    {
        this.productName = productName;
    }
    
    public String getContractNumber()
    {
        return contractNumber;
    }
    
    public void setContractNumber(String contractNumber)
    {
        this.contractNumber = contractNumber;
    }
    
    public String getCustomer()
    {
        return customer;
    }
    
    public void setCustomer(String customer)
    {
        this.customer = customer;
    }
    
    public String getTestUnit()
    {
        return testUnit;
    }
    
    public void setTestUnit(String testUnit)
    {
        this.testUnit = testUnit;
    }
    
    public String getMarketingCenter()
    {
        return marketingCenter;
    }
    
    public void setMarketingCenter(String marketingCenter)
    {
        this.marketingCenter = marketingCenter;
    }
    
    public String getAnalType()
    {
        return analType;
    }
    
    public void setAnalType(String analType)
    {
        this.analType = analType;
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
    
    public String getPath()
    {
        return path;
    }
    
    public void setPath(String path)
    {
        this.path = path;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getWordPath()
    {
        return wordPath;
    }
    
    public void setWordPath(String wordPath)
    {
        this.wordPath = wordPath;
    }
    
    public String getInspectMan()
    {
        return inspectMan;
    }
    
    public void setInspectMan(String inspectMan)
    {
        this.inspectMan = inspectMan;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getResubmit()
    {
        return resubmit;
    }

    public void setResubmit(Integer resubmit)
    {
        this.resubmit = resubmit;
    }

}
