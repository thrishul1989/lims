package com.todaysoft.lims.system.modules.bpm.tecanalys.model.analysis;

import java.util.List;

public class CnvAnalysisPicResultList {
	private CnvAnalysisPic cnvAnalysisPic;
    private List<CnvAnalysisResult> cnvAnalysisResultList;
    
	public CnvAnalysisPic getCnvAnalysisPic() {
		return cnvAnalysisPic;
	}
	public void setCnvAnalysisPic(CnvAnalysisPic cnvAnalysisPic) {
		this.cnvAnalysisPic = cnvAnalysisPic;
	}
	public List<CnvAnalysisResult> getCnvAnalysisResultList() {
		return cnvAnalysisResultList;
	}
	public void setCnvAnalysisResultList(List<CnvAnalysisResult> cnvAnalysisResultList) {
		this.cnvAnalysisResultList = cnvAnalysisResultList;
	}
    
    
}
