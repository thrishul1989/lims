package com.todaysoft.lims.testing.firstpcrsanger.model;

import java.math.BigDecimal;

public class FirstPcrSangerTaskVariables
{
    private String pcrTestCode;

    private String forwardPrimerName;

    private BigDecimal sampleInputQuantity;

    private String primerId;

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

    public String getForwardPrimerName() {
        return forwardPrimerName;
    }

    public void setForwardPrimerName(String forwardPrimerName) {
        this.forwardPrimerName = forwardPrimerName;
    }

    public String getPrimerId() {
        return primerId;
    }

    public void setPrimerId(String primerId) {
        this.primerId = primerId;
    }

    public BigDecimal getSampleInputQuantity() {
        return sampleInputQuantity;
    }

    public void setSampleInputQuantity(BigDecimal sampleInputQuantity) {
        this.sampleInputQuantity = sampleInputQuantity;
    }
}
