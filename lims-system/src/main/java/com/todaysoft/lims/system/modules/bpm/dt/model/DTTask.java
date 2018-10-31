package com.todaysoft.lims.system.modules.bpm.dt.model;

import java.math.BigDecimal;
import java.util.Date;

public class DTTask
{
    private String id;
    
    private String sampleName;
    
    private String sampleCode;
    
    private BigDecimal concentration;
    
    private BigDecimal volume;
    
    private BigDecimal ratio2628;
    
    private BigDecimal ratio2623;
    
    private String dnaLocation;
    
    private String primers;
    
    private BigDecimal inputQuantity;//投入量
    
    private BigDecimal addSampleVolume;//加样体积
    
    private BigDecimal addWaterVolume;//加水体积
    
    private boolean resubmit;
    
    private Integer resubmitCount;
    
    private String testCode;
    
    private Integer orderFlag;//排序
    
    private String products;
    
    private String combineTaskId;
    
    private String sampleTypeName;//样本类型
    
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
    
    public String getSampleName()
    {
        return sampleName;
    }
    
    public void setSampleName(String sampleName)
    {
        this.sampleName = sampleName;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public BigDecimal getConcentration()
    {
        return concentration;
    }
    
    public void setConcentration(BigDecimal concentration)
    {
        this.concentration = concentration;
    }
    
    public BigDecimal getVolume()
    {
        return volume;
    }
    
    public void setVolume(BigDecimal volume)
    {
        this.volume = volume;
    }
    
    public BigDecimal getRatio2628()
    {
        return ratio2628;
    }
    
    public void setRatio2628(BigDecimal ratio2628)
    {
        this.ratio2628 = ratio2628;
    }
    
    public BigDecimal getRatio2623()
    {
        return ratio2623;
    }
    
    public void setRatio2623(BigDecimal ratio2623)
    {
        this.ratio2623 = ratio2623;
    }
    
    public String getDnaLocation()
    {
        return dnaLocation;
    }
    
    public void setDnaLocation(String dnaLocation)
    {
        this.dnaLocation = dnaLocation;
    }
    
    public String getPrimers()
    {
        return primers;
    }
    
    public void setPrimers(String primers)
    {
        this.primers = primers;
    }
    
    public BigDecimal getInputQuantity()
    {
        return inputQuantity;
    }
    
    public void setInputQuantity(BigDecimal inputQuantity)
    {
        this.inputQuantity = inputQuantity;
    }
    
    public BigDecimal getAddSampleVolume()
    {
        return addSampleVolume;
    }
    
    public void setAddSampleVolume(BigDecimal addSampleVolume)
    {
        this.addSampleVolume = addSampleVolume;
    }
    
    public BigDecimal getAddWaterVolume()
    {
        return addWaterVolume;
    }
    
    public void setAddWaterVolume(BigDecimal addWaterVolume)
    {
        this.addWaterVolume = addWaterVolume;
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
    
    public String getTestCode()
    {
        return testCode;
    }
    
    public void setTestCode(String testCode)
    {
        this.testCode = testCode;
    }
    
    public Integer getOrderFlag()
    {
        return orderFlag;
    }
    
    public void setOrderFlag(Integer orderFlag)
    {
        this.orderFlag = orderFlag;
    }
    
    public String getProducts()
    {
        return products;
    }
    
    public void setProducts(String products)
    {
        this.products = products;
    }
    
    public DTTask(String primers)
    {
        this.primers = primers;
    }
    
    public DTTask()
    {
    }
    
    public String getCombineTaskId()
    {
        return combineTaskId;
    }
    
    public void setCombineTaskId(String combineTaskId)
    {
        this.combineTaskId = combineTaskId;
    }
    
    public String getSampleTypeName()
    {
        return sampleTypeName;
    }
    
    public void setSampleTypeName(String sampleTypeName)
    {
        this.sampleTypeName = sampleTypeName;
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
