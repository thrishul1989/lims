package com.todaysoft.lims.technical.mybatis.entity;

import java.util.Date;
import java.util.List;

public class BiologyFamilyAnalysis {
    private String id;

    private String code;

    private String createId;

    private Date createTime;

    private String creatorName;

    private Date updateTime;

    private String updatorName;

    private Boolean deleted;

    private Date deleteTime;

    private String deletorName;
    
    private List<OrderSampleDetail> sampleList;

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

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdatorName() {
        return updatorName;
    }

    public void setUpdatorName(String updatorName) {
        this.updatorName = updatorName;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

    public String getDeletorName() {
        return deletorName;
    }

    public void setDeletorName(String deletorName) {
        this.deletorName = deletorName;
    }

    public List<OrderSampleDetail> getSampleList()
    {
        return sampleList;
    }

    public void setSampleList(List<OrderSampleDetail> sampleList)
    {
        this.sampleList = sampleList;
    }
}