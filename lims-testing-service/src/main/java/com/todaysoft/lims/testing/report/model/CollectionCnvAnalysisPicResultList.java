package com.todaysoft.lims.testing.report.model;

import java.util.List;


public class CollectionCnvAnalysisPicResultList {
	private CnvAnalysisPic cnvAnalysisPic;
    private List<CollectionCnvAnalysisResult> collectionCnvAnalysisResult;
    
	public CnvAnalysisPic getCnvAnalysisPic() {
		return cnvAnalysisPic;
	}
	public void setCnvAnalysisPic(CnvAnalysisPic cnvAnalysisPic) {
		this.cnvAnalysisPic = cnvAnalysisPic;
	}
    public List<CollectionCnvAnalysisResult> getCollectionCnvAnalysisResult()
    {
        return collectionCnvAnalysisResult;
    }
    public void setCollectionCnvAnalysisResult(List<CollectionCnvAnalysisResult> collectionCnvAnalysisResult)
    {
        this.collectionCnvAnalysisResult = collectionCnvAnalysisResult;
    }
    
}
