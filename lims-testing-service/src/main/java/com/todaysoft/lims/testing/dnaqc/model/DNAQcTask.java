package com.todaysoft.lims.testing.dnaqc.model;

import java.util.Date;

public class DNAQcTask
{
    private String id;
    
    private String testingCode;
    
    private String orderType;
    
    private String products;
    
    private String testingMethods;
    
    private String originalSampleCode;
    
    private String originalSampleName;
    
    private String originalSampleTypeId;
    
    private String originalSampleTypeName;
    
    private String originalSamplePurpose;
    
    private Date originalSamplingDate;
    
    private String sampleCode;
    
    private boolean resubmit;
    
    private Integer resubmitCount;
    
    private String location;
    
    private String remark;
    
    private Integer storageStatus;
    
    private Integer ifUrgent;
    
    private Date urgentUpdateTime;
    
    private String urgentName;
    
    private Date plannedFinishDate;
    
    private Date startTime;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getTestingCode()
    {
        return testingCode;
    }
    
    public void setTestingCode(String testingCode)
    {
        this.testingCode = testingCode;
    }
    
    public String getOrderType()
    {
        return orderType;
    }
    
    public void setOrderType(String orderType)
    {
        this.orderType = orderType;
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
    
    public String getOriginalSampleCode()
    {
        return originalSampleCode;
    }
    
    public void setOriginalSampleCode(String originalSampleCode)
    {
        this.originalSampleCode = originalSampleCode;
    }
    
    public String getOriginalSampleName()
    {
        return originalSampleName;
    }
    
    public void setOriginalSampleName(String originalSampleName)
    {
        this.originalSampleName = originalSampleName;
    }
    
    public String getOriginalSampleTypeId()
    {
        return originalSampleTypeId;
    }
    
    public void setOriginalSampleTypeId(String originalSampleTypeId)
    {
        this.originalSampleTypeId = originalSampleTypeId;
    }
    
    public String getOriginalSampleTypeName()
    {
        return originalSampleTypeName;
    }
    
    public void setOriginalSampleTypeName(String originalSampleTypeName)
    {
        this.originalSampleTypeName = originalSampleTypeName;
    }
    
    public String getOriginalSamplePurpose()
    {
        return originalSamplePurpose;
    }
    
    public void setOriginalSamplePurpose(String originalSamplePurpose)
    {
        this.originalSamplePurpose = originalSamplePurpose;
    }
    
    public Date getOriginalSamplingDate()
    {
        return originalSamplingDate;
    }
    
    public void setOriginalSamplingDate(Date originalSamplingDate)
    {
        this.originalSamplingDate = originalSamplingDate;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public boolean isResubmit()
    {
        return resubmit;
    }
    
    public void setResubmit(boolean resubmit)
    {
        this.resubmit = resubmit;
    }
    
    public Integer getResubmitCount()
    {
        return resubmitCount;
    }
    
    public void setResubmitCount(Integer resubmitCount)
    {
        this.resubmitCount = resubmitCount;
    }
    
    public String getLocation()
    {
        return location;
    }
    
    public void setLocation(String location)
    {
        this.location = location;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public Integer getStorageStatus()
    {
        return storageStatus;
    }
    
    public void setStorageStatus(Integer storageStatus)
    {
        this.storageStatus = storageStatus;
    }

    public Integer getIfUrgent()
    {
        return ifUrgent;
    }

    public void setIfUrgent(Integer ifUrgent)
    {
        this.ifUrgent = ifUrgent;
    }

    public Date getUrgentUpdateTime()
    {
        return urgentUpdateTime;
    }

    public void setUrgentUpdateTime(Date urgentUpdateTime)
    {
        this.urgentUpdateTime = urgentUpdateTime;
    }

    public String getUrgentName()
    {
        return urgentName;
    }

    public void setUrgentName(String urgentName)
    {
        this.urgentName = urgentName;
    }

    public Date getPlannedFinishDate()
    {
        return plannedFinishDate;
    }

    public void setPlannedFinishDate(Date plannedFinishDate)
    {
        this.plannedFinishDate = plannedFinishDate;
    }

    public Date getStartTime()
    {
        return startTime;
    }

    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }
    
}
