package com.todaysoft.lims.system.modules.bpm.competeTasks.model.ngs;

import java.math.BigDecimal;
import java.util.Date;

public class LibraryQcTask
{
    private String id;
    
    private String testingCode;
    
    private String orderType;
    
    private String products;
    
    private String originalSampleCode;
    
    private String originalSampleName;
    
    private String originalSampleTypeId;
    
    private String originalSampleTypeName;
    
    private Date originalSamplingDate;
    
    private String sampleCode;
    
    private boolean resubmit;
    
    private Integer resubmitCount;
    
    private String location;
    
    private BigDecimal concn;
    
    private BigDecimal volume;
    
    private BigDecimal ratio2628;
    
    private BigDecimal ratio2623;
    
    private String qualityLevel;
    
    private boolean qualified;
    
    private String unqualifiedRemark;
    
    private String unqualifiedStrategy;

    private BigDecimal fragmentLengthStart;

    private BigDecimal fragmentLengthEnd;

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
    
    public boolean isQualified()
    {
        return qualified;
    }
    
    public void setQualified(boolean qualified)
    {
        this.qualified = qualified;
    }
    
    public String getUnqualifiedRemark()
    {
        return unqualifiedRemark;
    }
    
    public void setUnqualifiedRemark(String unqualifiedRemark)
    {
        this.unqualifiedRemark = unqualifiedRemark;
    }
    
    public String getUnqualifiedStrategy()
    {
        return unqualifiedStrategy;
    }
    
    public void setUnqualifiedStrategy(String unqualifiedStrategy)
    {
        this.unqualifiedStrategy = unqualifiedStrategy;
    }
}
