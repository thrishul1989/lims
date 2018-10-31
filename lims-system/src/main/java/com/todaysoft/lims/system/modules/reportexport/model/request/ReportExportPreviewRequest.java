package com.todaysoft.lims.system.modules.reportexport.model.request;

import java.util.List;

public class ReportExportPreviewRequest
{
    private String taskId;
    private String analysisResult;
    private List<String> recordIds;
    private String technicalFamilyGroupId;      //与analysisSampleId值相同
    private String analysisSampleId;            //与technicalFamilyGroupId值相同
    private List<String> cnvAnalysisPicIds;
    private List<String> cnvAnalysisResultIds;
    private List<String> dtDetectionResultIds;
    private List<String> mlpaDetectionResultIds;
    public String getTaskId()
    {
        return taskId;
    }
    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }
    public List<String> getRecordIds()
    {
        return recordIds;
    }
    public void setRecordIds(List<String> recordIds)
    {
        this.recordIds = recordIds;
    }
    public String getTechnicalFamilyGroupId()
    {
        return technicalFamilyGroupId;
    }
    public void setTechnicalFamilyGroupId(String technicalFamilyGroupId)
    {
        this.technicalFamilyGroupId = technicalFamilyGroupId;
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
    public List<String> getCnvAnalysisResultIds()
    {
        return cnvAnalysisResultIds;
    }
    public void setCnvAnalysisResultIds(List<String> cnvAnalysisResultIds)
    {
        this.cnvAnalysisResultIds = cnvAnalysisResultIds;
    }
    public String getAnalysisResult()
    {
        return analysisResult;
    }
    public void setAnalysisResult(String analysisResult)
    {
        this.analysisResult = analysisResult;
    }
    public List<String> getDtDetectionResultIds()
    {
        return dtDetectionResultIds;
    }
    public void setDtDetectionResultIds(List<String> dtDetectionResultIds)
    {
        this.dtDetectionResultIds = dtDetectionResultIds;
    }
    public List<String> getMlpaDetectionResultIds()
    {
        return mlpaDetectionResultIds;
    }
    public void setMlpaDetectionResultIds(List<String> mlpaDetectionResultIds)
    {
        this.mlpaDetectionResultIds = mlpaDetectionResultIds;
    }
}
