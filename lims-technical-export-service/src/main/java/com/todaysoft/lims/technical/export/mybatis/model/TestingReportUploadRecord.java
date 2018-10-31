package com.todaysoft.lims.technical.export.mybatis.model;

import java.util.Date;

public class TestingReportUploadRecord {
    
    public static Integer UPLOADTYPE_MANUAL=1;  //手动上传
    
    public static Integer UPLOADTYPE_AUTO=2;    //自动上传
    
    private String id;

    private String reportId;

    private String fileName;

    private Date uploadTime;

    private Integer uploadType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId == null ? null : reportId.trim();
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public Date getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Integer getUploadType() {
        return uploadType;
    }

    public void setUploadType(Integer uploadType) {
        this.uploadType = uploadType;
    }
}