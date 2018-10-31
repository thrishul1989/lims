package com.todaysoft.lims.testing.base.entity;

import com.todaysoft.lims.persist.UuidKeyEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name="LIMS_TESTING_DT_TASK")
public class TestingDTTask extends UuidKeyEntity implements Serializable{

    private static final long serialVersionUID = -259800291867202731L;

    private String sheetId;

    private String testingTaskId;

    private String testCode;

    private TestingSample testingSample;

    private BigDecimal addSampleVolume;//加样体积

    private BigDecimal addWaterVolume;//补水体积

    private String forwardPrimerName;

    private Product product;

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

    @Column(name="ORDER_FLAG")
    public Integer getOrderFlag() {
        return orderFlag;
    }

    public void setOrderFlag(Integer orderFlag) {
        this.orderFlag = orderFlag;
    }

    @Column(name="FORWARD_PRIMER_NAME")
    public String getForwardPrimerName() {
        return forwardPrimerName;
    }

    public void setForwardPrimerName(String forwardPrimerName) {
        this.forwardPrimerName = forwardPrimerName;
    }

    @ManyToOne
    @JoinColumn(name="PRODUCT_ID")
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
