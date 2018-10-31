package com.todaysoft.lims.system.modules.bpm.tecanalys.model.request;


public class CompareSampleReanalysisRequest {
	private String sampleAnalysisId;
	private CapCnvAnalysisRequest capCnvAnalysisRequest;
	
	public String getSampleAnalysisId() {
		return sampleAnalysisId;
	}
	public void setSampleAnalysisId(String sampleAnalysisId) {
		this.sampleAnalysisId = sampleAnalysisId;
	}
	public CapCnvAnalysisRequest getCapCnvAnalysisRequest() {
		return capCnvAnalysisRequest;
	}
	public void setCapCnvAnalysisRequest(CapCnvAnalysisRequest capCnvAnalysisRequest) {
		this.capCnvAnalysisRequest = capCnvAnalysisRequest;
	}

}
