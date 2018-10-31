package com.todaysoft.lims.testing.report.request;

import java.util.List;

public class ReportExportDetectionResultInfoRequest
{
    private String taskId;
    private String methodName;//MLPA,动态突变
    private List<String> detectionResultIds;
    
    public String getTaskId()
    {
        return taskId;
    }
    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }
    public String getMethodName()
    {
        return methodName;
    }
    public void setMethodName(String methodName)
    {
        this.methodName = methodName;
    }
    public List<String> getDetectionResultIds()
    {
        return detectionResultIds;
    }
    public void setDetectionResultIds(List<String> detectionResultIds)
    {
        this.detectionResultIds = detectionResultIds;
    }

}
