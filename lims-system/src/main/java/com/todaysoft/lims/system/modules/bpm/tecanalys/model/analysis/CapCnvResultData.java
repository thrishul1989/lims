package com.todaysoft.lims.system.modules.bpm.tecanalys.model.analysis;



import java.util.List;

public class CapCnvResultData{

    private String sampleAnalysisId;
    
    private String cnvAnalysisPicId;

    private List<CnvAnalysisResult> cnvAnalysisResult;

    public String getSampleAnalysisId() {
        return sampleAnalysisId;
    }

    public void setSampleAnalysisId(String sampleAnalysisId) {
        this.sampleAnalysisId = sampleAnalysisId;
    }

    public List<CnvAnalysisResult> getCnvAnalysisResult() {
        return cnvAnalysisResult;
    }

    public void setCnvAnalysisResult(List<CnvAnalysisResult> cnvAnalysisResult) {
        this.cnvAnalysisResult = cnvAnalysisResult;
    }

	public String getCnvAnalysisPicId() {
		return cnvAnalysisPicId;
	}

	public void setCnvAnalysisPicId(String cnvAnalysisPicId) {
		this.cnvAnalysisPicId = cnvAnalysisPicId;
	}
}
