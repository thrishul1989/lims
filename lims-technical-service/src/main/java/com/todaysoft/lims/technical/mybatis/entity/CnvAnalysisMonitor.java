package com.todaysoft.lims.technical.mybatis.entity;

import java.util.Date;

public class CnvAnalysisMonitor {

    public static Integer ANALYSIS_GOING_STAUS=0;

    public static Integer ANALYSIS_FAIL_STAUS=1;

    public static Integer ANALYSIS_SUCCESS_STAUS=2;
    
    public static Integer LIMS_BIOLOGY_TYPE=1;

    public static Integer CLOUD_BIOLOGY_TYPE=2;

    private String id;

    private String sampleTestId;

    private String taskId;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSampleTestId() {
        return sampleTestId;
    }

    public void setSampleTestId(String sampleTestId) {
        this.sampleTestId = sampleTestId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}