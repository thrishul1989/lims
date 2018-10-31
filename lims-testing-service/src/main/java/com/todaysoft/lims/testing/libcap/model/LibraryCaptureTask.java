package com.todaysoft.lims.testing.libcap.model;

import java.math.BigDecimal;
import java.util.Date;

public class LibraryCaptureTask
{
    private String id;
    
    private String testingCode;
    
    private String orderType;
    
    private String orderCode;
    
    private String products;
    
    private String originalSampleCode;
    
    private String originalSampleName;
    
    private String originalSampleTypeId;
    
    private String originalSampleTypeName;
    
    private Date originalSamplingDate;
    
    private String sampleTypeId;
    
    private String sampleTypeName;
    
    private String sampleCode;
    
    private boolean resubmit;
    
    private Integer resubmitCount;
    
    private String location;
    
    private String purpose;//用途
    
    private BigDecimal probeDataSize;//数据量
    
    // 文库样本特殊属性
    private String libraryIndex;
    
    private String probes;//关联探针
    
    private BigDecimal concn;
    
    private BigDecimal volume;
    
    private BigDecimal ratio2628;
    
    private BigDecimal ratio2623;
    
    private String qualityLevel;
    
    private String contractCode;
    
    private String remark;
    
    private Integer storageStatus;
    
    private Integer ifUrgent;
    
    private Date urgentUpdateTime;
    
    private String urgentName;
    
    private Date plannedFinishDate;

    private BigDecimal fragmentLengthStart;

    private BigDecimal fragmentLengthEnd;
    
    private Date startTime;
    
    public String getOrderCode()
    {
        return orderCode;
    }
    
    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }
    
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
    
    public String getProbes()
    {
        return probes;
    }
    
    public void setProbes(String probes)
    {
        this.probes = probes;
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
    
    public Date getOriginalSamplingDate()
    {
        return originalSamplingDate;
    }
    
    public void setOriginalSamplingDate(Date originalSamplingDate)
    {
        this.originalSamplingDate = originalSamplingDate;
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
    
    public String getLibraryIndex()
    {
        return libraryIndex;
    }
    
    public void setLibraryIndex(String libraryIndex)
    {
        this.libraryIndex = libraryIndex;
    }
    
    public BigDecimal getConcn()
    {
        return concn;
    }
    
    public void setConcn(BigDecimal concn)
    {
        this.concn = concn;
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
    
    public String getQualityLevel()
    {
        return qualityLevel;
    }
    
    public void setQualityLevel(String qualityLevel)
    {
        this.qualityLevel = qualityLevel;
    }
    
    public String getPurpose()
    {
        return purpose;
    }
    
    public void setPurpose(String purpose)
    {
        this.purpose = purpose;
    }
    
    public BigDecimal getProbeDataSize()
    {
        return probeDataSize;
    }
    
    public void setProbeDataSize(BigDecimal probeDataSize)
    {
        this.probeDataSize = probeDataSize;
    }
    
    public String getContractCode()
    {
        return contractCode;
    }
    
    public void setContractCode(String contractCode)
    {
        this.contractCode = contractCode;
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
    
    public BigDecimal getFragmentLengthStart() {
        return fragmentLengthStart;
    }

    public void setFragmentLengthStart(BigDecimal fragmentLengthStart) {
        this.fragmentLengthStart = fragmentLengthStart;
    }

    public BigDecimal getFragmentLengthEnd() {
        return fragmentLengthEnd;
    }

    public void setFragmentLengthEnd(BigDecimal fragmentLengthEnd) {
        this.fragmentLengthEnd = fragmentLengthEnd;
    }
}
