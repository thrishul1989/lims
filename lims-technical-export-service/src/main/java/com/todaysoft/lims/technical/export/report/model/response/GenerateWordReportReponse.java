package com.todaysoft.lims.technical.export.report.model.response;

public class GenerateWordReportReponse
{
    private String taskId;
    private String reportUrl;
    private String reportName;
    public String getTaskId()
    {
        return taskId;
    }
    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }
    public String getReportUrl()
    {
        return reportUrl;
    }
    public void setReportUrl(String reportUrl)
    {
        this.reportUrl = reportUrl;
    }
    public String getReportName()
    {
        return reportName;
    }
    public void setReportName(String reportName)
    {
        this.reportName = reportName;
    }
    
}
