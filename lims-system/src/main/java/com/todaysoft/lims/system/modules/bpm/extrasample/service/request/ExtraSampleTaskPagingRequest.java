package com.todaysoft.lims.system.modules.bpm.extrasample.service.request;

public class ExtraSampleTaskPagingRequest
{
    private Integer pageNo;
    
    private Integer pageSize;
    
    private String marketingCenter;
    
    private String orderCode;
    
    private String sampleCode;
    
    private Integer purpose;
    
    private Integer status;

    private String projectManager;

    public String getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
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
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public Integer getPurpose()
    {
        return purpose;
    }
    
    public void setPurpose(Integer purpose)
    {
        this.purpose = purpose;
    }
}
