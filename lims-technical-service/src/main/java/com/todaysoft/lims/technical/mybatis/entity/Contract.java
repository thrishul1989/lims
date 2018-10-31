package com.todaysoft.lims.technical.mybatis.entity;

import java.util.Date;

public class Contract {
    private String id;

    private String code;

    private String name;

    private Date effectiveStart;

    private Date effectiveEnd;

    private String remark;

    private Integer marketingCenter;

    private String signUser;

    private Date signDate;

    private Integer hospitalAdmited;

    private Byte status;

    private String original;

    private String creatorId;

    private String creatorName;

    private Date createTime;

    private String updatorId;

    private String updatorName;

    private Date updateTime;

    private String confirmerId;

    private String confirmerName;

    private Date confirmTime;

    private Integer delFlag;

    private Date deleteTime;

    private Long amount;

    private Integer incomingAmount;

    private Integer startMode;

    private Integer invoiceForm;

    private String knotId;

    private String knotName;

    private Date knotTime;

    private Integer unreconciledAmount;

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

    public Date getEffectiveStart() {
        return effectiveStart;
    }

    public void setEffectiveStart(Date effectiveStart) {
        this.effectiveStart = effectiveStart;
    }

    public Date getEffectiveEnd() {
        return effectiveEnd;
    }

    public void setEffectiveEnd(Date effectiveEnd) {
        this.effectiveEnd = effectiveEnd;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getMarketingCenter() {
        return marketingCenter;
    }

    public void setMarketingCenter(Integer marketingCenter) {
        this.marketingCenter = marketingCenter;
    }

    public String getSignUser() {
        return signUser;
    }

    public void setSignUser(String signUser) {
        this.signUser = signUser;
    }

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    public Integer getHospitalAdmited() {
        return hospitalAdmited;
    }

    public void setHospitalAdmited(Integer hospitalAdmited) {
        this.hospitalAdmited = hospitalAdmited;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
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

    public String getUpdatorId() {
        return updatorId;
    }

    public void setUpdatorId(String updatorId) {
        this.updatorId = updatorId;
    }

    public String getUpdatorName() {
        return updatorName;
    }

    public void setUpdatorName(String updatorName) {
        this.updatorName = updatorName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getConfirmerId() {
        return confirmerId;
    }

    public void setConfirmerId(String confirmerId) {
        this.confirmerId = confirmerId;
    }

    public String getConfirmerName() {
        return confirmerName;
    }

    public void setConfirmerName(String confirmerName) {
        this.confirmerName = confirmerName;
    }

    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Integer getIncomingAmount() {
        return incomingAmount;
    }

    public void setIncomingAmount(Integer incomingAmount) {
        this.incomingAmount = incomingAmount;
    }

    public Integer getStartMode() {
        return startMode;
    }

    public void setStartMode(Integer startMode) {
        this.startMode = startMode;
    }

    public Integer getInvoiceForm() {
        return invoiceForm;
    }

    public void setInvoiceForm(Integer invoiceForm) {
        this.invoiceForm = invoiceForm;
    }

    public String getKnotId() {
        return knotId;
    }

    public void setKnotId(String knotId) {
        this.knotId = knotId;
    }

    public String getKnotName() {
        return knotName;
    }

    public void setKnotName(String knotName) {
        this.knotName = knotName;
    }

    public Date getKnotTime() {
        return knotTime;
    }

    public void setKnotTime(Date knotTime) {
        this.knotTime = knotTime;
    }

    public Integer getUnreconciledAmount() {
        return unreconciledAmount;
    }

    public void setUnreconciledAmount(Integer unreconciledAmount) {
        this.unreconciledAmount = unreconciledAmount;
    }
}