package com.todaysoft.lims.testing.dt.model;

import java.math.BigDecimal;

public class DTTaskVariables
{
    private String testCode;

    private BigDecimal addSampleVolume;//加样体积

    private BigDecimal addWaterVolume;//加水体积

    private String remark;

    public BigDecimal getAddSampleVolume() {
        return addSampleVolume;
    }

    public void setAddSampleVolume(BigDecimal addSampleVolume) {
        this.addSampleVolume = addSampleVolume;
    }

    public BigDecimal getAddWaterVolume() {
        return addWaterVolume;
    }

    public void setAddWaterVolume(BigDecimal addWaterVolume) {
        this.addWaterVolume = addWaterVolume;
    }

    public String getTestCode() {
        return testCode;
    }

    public void setTestCode(String testCode) {
        this.testCode = testCode;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
