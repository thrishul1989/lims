package com.todaysoft.lims.system.modules.reportexport.model.request;

import java.util.List;

public class ReportExportDetectionResultInfoRequest
{
    public static String METHODNAME_MLPA="MLPA";
    public static String METHODNAME_DT="动态突变";
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
