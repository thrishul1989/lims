package com.todaysoft.lims.testing.report.request;

public class GetReportExportSampleBaseInfoRequest
{
    private String taskId;
    private String sampleCode;
    private String analysisResult;
    public String getTaskId()
    {
        return taskId;
    }

    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }

    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }

    public String getAnalysisResult()
    {
        return analysisResult;
    }

    public void setAnalysisResult(String analysisResult)
    {
        this.analysisResult = analysisResult;
    }


}
