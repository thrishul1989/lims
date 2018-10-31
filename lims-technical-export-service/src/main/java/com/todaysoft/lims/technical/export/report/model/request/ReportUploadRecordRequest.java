package com.todaysoft.lims.technical.export.report.model.request;

public class ReportUploadRecordRequest
{
    public static Integer UPLOADTYPE_MANUAL=1;  //手动上传
    
    public static Integer UPLOADTYPE_AUTO=2;    //自动上传
    
    private String reportId;
    
    private String fileName;
    
    private Integer uploadType;

    public String getReportId()
    {
        return reportId;
    }

    public void setReportId(String reportId)
    {
        this.reportId = reportId;
    }

    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public Integer getUploadType()
    {
        return uploadType;
    }

    public void setUploadType(Integer uploadType)
    {
        this.uploadType = uploadType;
    }
    
}
