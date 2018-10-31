package com.todaysoft.lims.system.modules.bpm.libcap.model;

import java.math.BigDecimal;
import java.util.Date;

public class LibraryCaptureSheetSampleModel
{
    private String id;
    
    private String originalSampleCode;
    
    private String originalSampleName;
    
    private String originalSampleTypeId;
    
    private String originalSampleTypeName;
    
    private Date originalSamplingDate;
    
    private String sampleTypeId;
    
    private String sampleTypeName;
    
    private String sampleId;
    
    private String sampleCode;
    
    private BigDecimal inputSize;
    
    private BigDecimal inputVolume;
    
    // 文库样本特殊属性
    private String libraryIndex;
    
    private BigDecimal concn;
    
    private BigDecimal volume;
    
    private BigDecimal ratio2628;
    
    private BigDecimal ratio2623;
    
    private String qualityLevel;

    private BigDecimal fragmentLengthStart;

    private BigDecimal fragmentLengthEnd;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
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
    
    public String getSampleId()
    {
        return sampleId;
    }
    
    public void setSampleId(String sampleId)
    {
        this.sampleId = sampleId;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public BigDecimal getInputSize()
    {
        return inputSize;
    }
    
    public void setInputSize(BigDecimal inputSize)
    {
        this.inputSize = inputSize;
    }
    
    public BigDecimal getInputVolume()
    {
        return inputVolume;
    }
    
    public void setInputVolume(BigDecimal inputVolume)
    {
        this.inputVolume = inputVolume;
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
