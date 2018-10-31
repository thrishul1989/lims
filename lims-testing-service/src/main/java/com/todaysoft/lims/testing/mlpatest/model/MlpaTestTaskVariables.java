package com.todaysoft.lims.testing.mlpatest.model;

import java.math.BigDecimal;

public class MlpaTestTaskVariables
{
    private String testCode;

    private Integer testFlag;// 1-检测 空的也是默认检测  2-验证

    private BigDecimal addSampleVolume;//加样体积

    private BigDecimal addWaterVolume;//加水体积

    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

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

    public Integer getTestFlag() {
        return testFlag;
    }

    public void setTestFlag(Integer testFlag) {
        this.testFlag = testFlag;
    }
}
