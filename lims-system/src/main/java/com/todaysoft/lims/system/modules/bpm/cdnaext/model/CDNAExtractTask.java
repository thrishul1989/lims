package com.todaysoft.lims.system.modules.bpm.cdnaext.model;

import java.math.BigDecimal;
import java.util.Date;

public class CDNAExtractTask
{
    private String id;
    
    private String testingCode;
    
    private String orderType;
    
    private String sampleCode;
    
    private String sampleName;
    
    private String sampleTypeId;
    
    private String sampleTypeName;
    
    private String products;
    
    private Date samplingDate;
    
    private boolean resubmit;
    
    private Integer resubmitCount;
    
    private BigDecimal sampleInputSize;
    
    private String location;
    
    private String outputSampleCode;
    
    private BigDecimal outputSampleSize;
    
    private String remark;
    
    private Integer storageStatus;
    
    private Integer ifUrgent;
    
    private Date urgentUpdateTime;
    
    private String urgentName;
    
    private Date plannedFinishDate;
    
    private Date startTime;
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
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
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public String getSampleName()
    {
        return sampleName;
    }
    
    public void setSampleName(String sampleName)
    {
        this.sampleName = sampleName;
    }
    
    public String getSampleTypeId()
    {
        return sampleTypeId;
    }
    
    public void setSampleTypeId(String sampleTypeId)
    {
        this.sampleTypeId = sampleTypeId;
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
    
    public Date getSamplingDate()
    {
        return samplingDate;
    }
    
    public void setSamplingDate(Date samplingDate)
    {
        this.samplingDate = samplingDate;
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
    
    public BigDecimal getSampleInputSize()
    {
        return sampleInputSize;
    }
    
    public void setSampleInputSize(BigDecimal sampleInputSize)
    {
        this.sampleInputSize = sampleInputSize;
    }
    
    public String getOutputSampleCode()
    {
        return outputSampleCode;
    }
    
    public void setOutputSampleCode(String outputSampleCode)
    {
        this.outputSampleCode = outputSampleCode;
    }
    
    public BigDecimal getOutputSampleSize()
    {
        return outputSampleSize;
    }
    
    public void setOutputSampleSize(BigDecimal outputSampleSize)
    {
        this.outputSampleSize = outputSampleSize;
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
