package com.todaysoft.lims.system.modules.bpm.report.model;

public class ReportRequest
{
    private String reportId;
    
    private String fileName;
    
    public ReportRequest()
    {
    }
    
    public ReportRequest(String reportId, String fileName)
    {
        this.reportId = reportId;
        this.fileName = fileName;
    }
    
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
}
