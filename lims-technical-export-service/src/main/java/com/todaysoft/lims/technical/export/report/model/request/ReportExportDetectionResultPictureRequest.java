package com.todaysoft.lims.technical.export.report.model.request;

public class ReportExportDetectionResultPictureRequest
{
    private String taskId;
    private String method;
    
    public String getTaskId()
    {
        return taskId;
    }
    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }
    public String getMethod()
    {
        return method;
    }
    public void setMethod(String method)
    {
        this.method = method;
    }
    
}
