package com.todaysoft.lims.testing.base.entity;

import com.todaysoft.lims.persist.UuidKeyEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name="LIMS_TESTING_MLPA_TASK")
public class TestingMlpaTask extends UuidKeyEntity implements Serializable{

    private static final long serialVersionUID = -2404502440463732549L;

    private String sheetId;

    private String testingTaskId;

    private String testCode;

    private TestingSample testingSample;

    private BigDecimal addSampleVolume;//加样体积

    private BigDecimal addWaterVolume;//补水体积

    private Integer type;//1-检测 2-对照

    private Integer attrType;//1-待测 2-对照

    private String probe;

    private String remark;

    private Integer orderFlag;//排序

    @Column(name="SHEET_ID")
    public String getSheetId() {
        return sheetId;
    }

    public void setSheetId(String sheetId) {
        this.sheetId = sheetId;
    }

    @Column(name="TESTING_TASK_ID")
    public String getTestingTaskId() {
        return testingTaskId;
    }

    public void setTestingTaskId(String testingTaskId) {
        this.testingTaskId = testingTaskId;
    }

    @Column(name="TEST_CODE")
    public String getTestCode() {
        return testCode;
    }

    public void setTestCode(String testCode) {
        this.testCode = testCode;
    }

    @ManyToOne
    @JoinColumn(name="INPUT_SAMPLE_ID")
    public TestingSample getTestingSample() {
        return testingSample;
    }

    public void setTestingSample(TestingSample testingSample) {
        this.testingSample = testingSample;
    }

    @Column(name="SAMPLE_SIZE")
    public BigDecimal getAddSampleVolume() {
        return addSampleVolume;
    }

    public void setAddSampleVolume(BigDecimal addSampleVolume) {
        this.addSampleVolume = addSampleVolume;
    }

    @Column(name="TE_SZIE")
    public BigDecimal getAddWaterVolume() {
        return addWaterVolume;
    }

    public void setAddWaterVolume(BigDecimal addWaterVolume) {
        this.addWaterVolume = addWaterVolume;
    }

    @Column(name="TYPE")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Column(name="ATTR_TYPE")
    public Integer getAttrType() {
        return attrType;
    }

    public void setAttrType(Integer attrType) {
        this.attrType = attrType;
    }

    @Column(name="PROBE")
    public String getProbe() {
        return probe;
    }

    public void setProbe(String probe) {
        this.probe = probe;
    }

    @Column(name="REMARK")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Column(name="ORDER_FLAG")
    public Integer getOrderFlag() {
        return orderFlag;
    }

    public void setOrderFlag(Integer orderFlag) {
        this.orderFlag = orderFlag;
    }
}
