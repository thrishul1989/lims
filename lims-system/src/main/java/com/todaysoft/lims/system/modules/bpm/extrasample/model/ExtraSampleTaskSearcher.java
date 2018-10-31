package com.todaysoft.lims.system.modules.bpm.extrasample.model;

public class ExtraSampleTaskSearcher
{
    private String marketingCenter;
    
    private String sampleCode;
    
    private Integer purpose;
    
    private String orderCode;
    
    private Integer status;

    private String projectManager;

    private String prjManagerName;

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

    public Integer getStatus()
    {
        return status;
    }
    
    public void setStatus(Integer status)
    {
        this.status = status;
    }
    
    public String getMarketingCenter()
    {
        return marketingCenter;
    }
    
    public void setMarketingCenter(String marketingCenter)
    {
        this.marketingCenter = marketingCenter;
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
    
    public String getOrderCode()
    {
        return orderCode;
    }
    
    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }
}
