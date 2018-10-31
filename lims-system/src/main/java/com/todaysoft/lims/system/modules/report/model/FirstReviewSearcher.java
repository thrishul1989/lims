package com.todaysoft.lims.system.modules.report.model;

public class FirstReviewSearcher
{
    private String reportCode;
    
    private String technicalMan;
    
    private String sampleCode;
    
    private String productName;
    
    private String analType;
    
    private String marketingCenter;
    
    private int pageNo;
    
    private int pageSize;
    
    private String orderCode;
    
    private String inspectMan;
    
    private String reportDateStart;//申请时间
    
    private String reportDateEnd;
    
    private String productLeader;
    
    public String getReportCode()
    {
        return reportCode;
    }
    
    public void setReportCode(String reportCode)
    {
        this.reportCode = reportCode;
    }
    
    public String getTechnicalMan()
    {
        return technicalMan;
    }
    
    public void setTechnicalMan(String technicalMan)
    {
        this.technicalMan = technicalMan;
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
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public String getProductName()
    {
        return productName;
    }
    
    public void setProductName(String productName)
    {
        this.productName = productName;
    }
    
    public String getAnalType()
    {
        return analType;
    }
    
    public void setAnalType(String analType)
    {
        this.analType = analType;
    }
    
    public String getMarketingCenter()
    {
        return marketingCenter;
    }
    
    public void setMarketingCenter(String marketingCenter)
    {
        this.marketingCenter = marketingCenter;
    }
    
    public String getOrderCode()
    {
        return orderCode;
    }
    
    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }
    
    public String getInspectMan()
    {
        return inspectMan;
    }
    
    public void setInspectMan(String inspectMan)
    {
        this.inspectMan = inspectMan;
    }
    
    public String getReportDateStart()
    {
        return reportDateStart;
    }
    
    public void setReportDateStart(String reportDateStart)
    {
        this.reportDateStart = reportDateStart;
    }
    
    public String getReportDateEnd()
    {
        return reportDateEnd;
    }
    
    public void setReportDateEnd(String reportDateEnd)
    {
        this.reportDateEnd = reportDateEnd;
    }
    
    public String getProductLeader()
    {
        return productLeader;
    }
    
    public void setProductLeader(String productLeader)
    {
        this.productLeader = productLeader;
    }
}
