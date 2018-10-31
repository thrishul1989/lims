package com.todaysoft.lims.technical.model.response;

import java.util.List;

import com.todaysoft.lims.technical.mybatis.entity.CnvAnalysisPic;
import com.todaysoft.lims.technical.mybatis.entity.CnvAnalysisResult;

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
