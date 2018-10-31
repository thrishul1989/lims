package com.todaysoft.lims.testing.report.model;

public class SendDataRequest
{
    private String reportId; 
    
    private String reportTemplateType;
    
    private String reportType;
    
    private String analyResult;
    
    private Integer status;

    public String getReportId()
    {
        return reportId;
    }

    public void setReportId(String reportId)
    {
        this.reportId = reportId;
    }

    public String getReportTemplateType()
    {
        return reportTemplateType;
    }

    public void setReportTemplateType(String reportTemplateType)
    {
        this.reportTemplateType = reportTemplateType;
    }

    public Integer getStatus()
    {
        return status;
    }

    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public String getReportType()
    {
        return reportType;
    }

    public void setReportType(String reportType)
    {
        this.reportType = reportType;
    }

    public String getAnalyResult()
    {
        return analyResult;
    }

    public void setAnalyResult(String analyResult)
    {
        this.analyResult = analyResult;
    }
    
}
