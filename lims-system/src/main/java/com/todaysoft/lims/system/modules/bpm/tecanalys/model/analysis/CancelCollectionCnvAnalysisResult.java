package com.todaysoft.lims.system.modules.bpm.tecanalys.model.analysis;

public class CancelCollectionCnvAnalysisResult
{
    
    private String technicalAnalyRecordId; //LIMS_TESTING_TECHNICAL_ANALY_RECORD主键
    
    private String cnvAnalysisResultId; //LIMS_TESTING_TECHNICAL_ANALY_CNV_ANALYSIS_RESULT主键
    
    public String getTechnicalAnalyRecordId()
    {
        return technicalAnalyRecordId;
    }
    
    public void setTechnicalAnalyRecordId(String technicalAnalyRecordId)
    {
        this.technicalAnalyRecordId = technicalAnalyRecordId;
    }
    
    public String getCnvAnalysisResultId()
    {
        return cnvAnalysisResultId;
    }
    
    public void setCnvAnalysisResultId(String cnvAnalysisResultId)
    {
        this.cnvAnalysisResultId = cnvAnalysisResultId;
    }
    
}
