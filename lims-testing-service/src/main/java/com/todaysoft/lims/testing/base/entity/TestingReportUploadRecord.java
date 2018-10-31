package com.todaysoft.lims.testing.base.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "TESTING_REPORT_UPLOAD_RECORD")
public class TestingReportUploadRecord extends UuidKeyEntity implements Serializable
{   
    private static final long serialVersionUID = 7176922604425201113L;
    
    public static Integer UPLOADTYPE_MANUAL=1;  //手动上传
    
    public static Integer UPLOADTYPE_AUTO=2;    //自动上传
    
    private String reportId;

    private String fileName;

    private Date uploadTime;

    private Integer uploadType;

    @Column(name = "REPORT_ID")
    public String getReportId()
    {
        return reportId;
    }

    public void setReportId(String reportId)
    {
        this.reportId = reportId;
    }

    @Column(name = "FILE_NAME")
    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    @Column(name = "UPLOAD_TIME")
    public Date getUploadTime()
    {
        return uploadTime;
    }

    public void setUploadTime(Date uploadTime)
    {
        this.uploadTime = uploadTime;
    }

    @Column(name = "UPLOAD_TYPE")
    public Integer getUploadType()
    {
        return uploadType;
    }

    public void setUploadType(Integer uploadType)
    {
        this.uploadType = uploadType;
    }
    
    
}
