package com.todaysoft.lims.testing.base.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BIOLOGY_DIVISION_TASK")
public class BiologyDivisionTask implements Serializable{

    private static final long serialVersionUID = 5232166091099214390L;

    public static final Integer WAITING_DIVISION_STATUS=0;//待拆分

    public static final Integer GOING_DIVISION_STATUS=1;//正在进行数据拆分

    public static final Integer MD5_DIVISION_STATUS=2;//生成md5校验文件

    public static final Integer COUNTTOTALDATA_DIVISION_STATUS=3;//统计数据产量

    public static final Integer CLEANCACH_DIVISION_STATUS=4;//清理缓存

    public static final Integer SUCCESS_DIVISION_STATUS=5;//拆分完成

    public static final Integer FAIL_DIVISION_STATUS=6;//拆分失败

    private String id;

    private String sequencingCode;

    private String sequencingType;

    private String runCode;

    private String laneCode;

    private Date startTime;

    private Date endTime;

    private Integer status;

    private Boolean resubmit;

    private Integer resubmitCount;

    private Boolean ifUrgent;

    private Date urgentUpdateTime;

    private String urgentName;

    private String resultContent;

    @Id
    @Column(name = "ID")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "SEQUENCING_CODE")
    public String getSequencingCode() {
        return sequencingCode;
    }

    public void setSequencingCode(String sequencingCode) {
        this.sequencingCode = sequencingCode;
    }

    @Column(name = "SEQUENCING_TYPE")
    public String getSequencingType() {
        return sequencingType;
    }

    public void setSequencingType(String sequencingType) {
        this.sequencingType = sequencingType;
    }

    @Column(name = "RUN_CODE")
    public String getRunCode() {
        return runCode;
    }

    public void setRunCode(String runCode) {
        this.runCode = runCode;
    }

    @Column(name = "LANE_CODE")
    public String getLaneCode() {
        return laneCode;
    }

    public void setLaneCode(String laneCode) {
        this.laneCode = laneCode;
    }

    @Column(name = "START_TIME")
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Column(name = "END_TIME")
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Column(name = "STATUS")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "RESUBMIT")
    public Boolean getResubmit() {
        return resubmit;
    }

    public void setResubmit(Boolean resubmit) {
        this.resubmit = resubmit;
    }

    @Column(name = "RESUBMIT_COUNT")
    public Integer getResubmitCount() {
        return resubmitCount;
    }

    public void setResubmitCount(Integer resubmitCount) {
        this.resubmitCount = resubmitCount;
    }

    @Column(name = "IF_URGENT")
    public Boolean getIfUrgent() {
        return ifUrgent;
    }

    public void setIfUrgent(Boolean ifUrgent) {
        this.ifUrgent = ifUrgent;
    }

    @Column(name = "URGENT_UPDATE_TIME")
    public Date getUrgentUpdateTime() {
        return urgentUpdateTime;
    }

    public void setUrgentUpdateTime(Date urgentUpdateTime) {
        this.urgentUpdateTime = urgentUpdateTime;
    }

    @Column(name = "URGENT_NAME")
    public String getUrgentName() {
        return urgentName;
    }

    public void setUrgentName(String urgentName) {
        this.urgentName = urgentName;
    }

    @Column(name = "RESULT_CONTENT")
    public String getResultContent() {
        return resultContent;
    }

    public void setResultContent(String resultContent) {
        this.resultContent = resultContent;
    }
}