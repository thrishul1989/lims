package com.todaysoft.lims.system.modules.bpm.model.assign.model.task;

import java.util.Date;
import java.util.List;

public class TechnicalAnalysisAssignTask
{
    private String id;
    
    private String orderType;
    
    private String sampleCode;
    
    private String sampleTypeKey;
    
    private String sampleTypeName;
    
    private String products;
    
    private String examinee;
    
    private Date samplingDate;
    
    private Integer historyStatus;
    
    private String testingCode;
    
    private List<String> testingRemarks;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getOrderType()
    {
        return orderType;
    }
    
    public void setOrderType(String orderType)
    {
        this.orderType = orderType;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public String getSampleTypeKey()
    {
        return sampleTypeKey;
    }
    
    public void setSampleTypeKey(String sampleTypeKey)
    {
        this.sampleTypeKey = sampleTypeKey;
    }
    
    public String getSampleTypeName()
    {
        return sampleTypeName;
    }
    
    public void setSampleTypeName(String sampleTypeName)
    {
        this.sampleTypeName = sampleTypeName;
    }
    
    public String getProducts()
    {
        return products;
    }
    
    public void setProducts(String products)
    {
        this.products = products;
    }
    
    public String getExaminee()
    {
        return examinee;
    }
    
    public void setExaminee(String examinee)
    {
        this.examinee = examinee;
    }
    
    public Date getSamplingDate()
    {
        return samplingDate;
    }
    
    public void setSamplingDate(Date samplingDate)
    {
        this.samplingDate = samplingDate;
    }
    
    public Integer getHistoryStatus()
    {
        return historyStatus;
    }
    
    public void setHistoryStatus(Integer historyStatus)
    {
        this.historyStatus = historyStatus;
    }
    
    public String getTestingCode()
    {
        return testingCode;
    }
    
    public void setTestingCode(String testingCode)
    {
        this.testingCode = testingCode;
    }
    
    public List<String> getTestingRemarks()
    {
        return testingRemarks;
    }
    
    public void setTestingRemarks(List<String> testingRemarks)
    {
        this.testingRemarks = testingRemarks;
    }
}
