package com.todaysoft.lims.testing.firstpcr.model;

import java.math.BigDecimal;

public class FirstPcrTaskVariables
{
    private BigDecimal sampleInputQuantity;

    private String pcrTestCode;

    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getSampleInputQuantity() {
        return sampleInputQuantity;
    }

    public void setSampleInputQuantity(BigDecimal sampleInputQuantity) {
        this.sampleInputQuantity = sampleInputQuantity;
    }

    public String getPcrTestCode() {
        return pcrTestCode;
    }

    public void setPcrTestCode(String pcrTestCode) {
        this.pcrTestCode = pcrTestCode;
    }
}
