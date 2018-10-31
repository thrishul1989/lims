package com.todaysoft.lims.system.modules.bpm.pcrngstest.model;

import java.math.BigDecimal;

public class PcrNgsTestAssignTaskArgs
{
    private String id;

    private String pcrTestCode;

    private Integer libraryIndex;

    private BigDecimal sampleInputSize; //样本投入量

    private BigDecimal sampleInputVolume;//加样体积

    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }

    public String getPcrTestCode() {
        return pcrTestCode;
    }

    public void setPcrTestCode(String pcrTestCode) {
        this.pcrTestCode = pcrTestCode;
    }

    public Integer getLibraryIndex() {
        return libraryIndex;
    }

    public void setLibraryIndex(Integer libraryIndex) {
        this.libraryIndex = libraryIndex;
    }

    public BigDecimal getSampleInputSize() {
        return sampleInputSize;
    }

    public void setSampleInputSize(BigDecimal sampleInputSize) {
        this.sampleInputSize = sampleInputSize;
    }

    public BigDecimal getSampleInputVolume() {
        return sampleInputVolume;
    }

    public void setSampleInputVolume(BigDecimal sampleInputVolume) {
        this.sampleInputVolume = sampleInputVolume;
    }
}
