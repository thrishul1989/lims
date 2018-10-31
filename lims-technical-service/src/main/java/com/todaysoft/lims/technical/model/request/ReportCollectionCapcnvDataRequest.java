package com.todaysoft.lims.technical.model.request;

import java.util.List;

public class ReportCollectionCapcnvDataRequest
{
    private String taskId;
    private String analysisSampleId;
    private List<String> cnvAnalysisPicIds;
    public String getTaskId()
    {
        return taskId;
    }
    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }
    public String getAnalysisSampleId()
    {
        return analysisSampleId;
    }
    public void setAnalysisSampleId(String analysisSampleId)
    {
        this.analysisSampleId = analysisSampleId;
    }
    public List<String> getCnvAnalysisPicIds()
    {
        return cnvAnalysisPicIds;
    }
    public void setCnvAnalysisPicIds(List<String> cnvAnalysisPicIds)
    {
        this.cnvAnalysisPicIds = cnvAnalysisPicIds;
    }
    
    
}
