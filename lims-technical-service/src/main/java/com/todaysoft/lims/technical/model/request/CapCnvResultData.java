package com.todaysoft.lims.technical.model.request;

import java.util.List;

import com.todaysoft.lims.technical.mybatis.entity.CnvAnalysisResult;

public class CapCnvResultData {

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
