package com.todaysoft.lims.testing.pcrngstest.model;

import java.math.BigDecimal;

public class PcrNgsTestTaskVariables
{
    private String pcrTestCode;

    private Integer libraryIndex;

    private BigDecimal sampleInputSize; //样本投入量

    private BigDecimal sampleInputVolume;//加样体积

    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
