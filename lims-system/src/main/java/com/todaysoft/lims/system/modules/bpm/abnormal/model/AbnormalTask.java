package com.todaysoft.lims.system.modules.bpm.abnormal.model;

import java.util.Date;

public class AbnormalTask
{
    private String id;
    
    private String name;
    
    private String marketingCenter;
    
    private String orderCode;
    
    private String products;
    
    private String testingMethods;
    
    private String receivedSampleType;
    
    private String receivedSampleName;
    
    private String receivedSampleCode;
    
    private String testingSampleCode;
    
    private String remark;
    
    private Date submitTime;
    
    private Integer resubmitCount;

    private String semantic;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
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
    
    public String getProducts()
    {
        return products;
    }
    
    public void setProducts(String products)
    {
        this.products = products;
    }
    
    public String getTestingMethods()
    {
        return testingMethods;
    }
    
    public void setTestingMethods(String testingMethods)
    {
        this.testingMethods = testingMethods;
    }
    
    public String getReceivedSampleType()
    {
        return receivedSampleType;
    }
    
    public void setReceivedSampleType(String receivedSampleType)
    {
        this.receivedSampleType = receivedSampleType;
    }
    
    public String getReceivedSampleName()
    {
        return receivedSampleName;
    }
    
    public void setReceivedSampleName(String receivedSampleName)
    {
        this.receivedSampleName = receivedSampleName;
    }
    
    public String getReceivedSampleCode()
    {
        return receivedSampleCode;
    }
    
    public void setReceivedSampleCode(String receivedSampleCode)
    {
        this.receivedSampleCode = receivedSampleCode;
    }
    
    public String getTestingSampleCode()
    {
        return testingSampleCode;
    }
    
    public void setTestingSampleCode(String testingSampleCode)
    {
        this.testingSampleCode = testingSampleCode;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public Date getSubmitTime()
    {
        return submitTime;
    }
    
    public void setSubmitTime(Date submitTime)
    {
        this.submitTime = submitTime;
    }

    public Integer getResubmitCount()
    {
        return resubmitCount;
    }

    public void setResubmitCount(Integer resubmitCount)
    {
        this.resubmitCount = resubmitCount;
    }

    public String getSemantic() {
        return semantic;
    }

    public void setSemantic(String semantic) {
        this.semantic = semantic;
    }
}
