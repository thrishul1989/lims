package com.todaysoft.lims.system.modules.bpm.bioanalysis.model.dataTemplate;

import java.util.Date;

public class DataTemplate {
    private String id;

    private String name;

    private Boolean delFlag;

    private Boolean priFlag;

    private Date createTime;

    private String createId;

    private String createName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Boolean delFlag) {
        this.delFlag = delFlag;
    }

    public Boolean getPriFlag() {
        return priFlag;
    }

    public void setPriFlag(Boolean priFlag) {
        this.priFlag = priFlag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }
}