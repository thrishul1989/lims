package com.todaysoft.lims.technical.mybatis.entity;

import java.util.Date;

public class Product {
    private String id;

    private String code;

    private String name;

    private String testingType;

    private String testingSubtype;

    private Integer testingDuration;

    private String testingStartSample;

    private Integer price;

    private String remark;

    private Boolean enabled;

    private String creatorId;

    private String creatorName;

    private Date createTime;

    private Integer delFlag;

    private Boolean ifMade;

    private String testInstitution;

    private Date updateTime;

    private String reportTemplateId;

    private String productDuty;

    private String samplePurpose;

    private String description;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTestingType() {
        return testingType;
    }

    public void setTestingType(String testingType) {
        this.testingType = testingType;
    }

    public String getTestingSubtype() {
        return testingSubtype;
    }

    public void setTestingSubtype(String testingSubtype) {
        this.testingSubtype = testingSubtype;
    }

    public Integer getTestingDuration() {
        return testingDuration;
    }

    public void setTestingDuration(Integer testingDuration) {
        this.testingDuration = testingDuration;
    }

    public String getTestingStartSample() {
        return testingStartSample;
    }

    public void setTestingStartSample(String testingStartSample) {
        this.testingStartSample = testingStartSample;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Boolean getIfMade() {
        return ifMade;
    }

    public void setIfMade(Boolean ifMade) {
        this.ifMade = ifMade;
    }

    public String getTestInstitution() {
        return testInstitution;
    }

    public void setTestInstitution(String testInstitution) {
        this.testInstitution = testInstitution;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getReportTemplateId() {
        return reportTemplateId;
    }

    public void setReportTemplateId(String reportTemplateId) {
        this.reportTemplateId = reportTemplateId;
    }

    public String getProductDuty() {
        return productDuty;
    }

    public void setProductDuty(String productDuty) {
        this.productDuty = productDuty;
    }

    public String getSamplePurpose() {
        return samplePurpose;
    }

    public void setSamplePurpose(String samplePurpose) {
        this.samplePurpose = samplePurpose;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}