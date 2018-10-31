package com.todaysoft.lims.testing.report.model;

public class ReportUploadRecordRequest
{
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
