package com.todaysoft.lims.technical.model.response;

import java.util.List;

import com.todaysoft.lims.technical.mybatis.entity.CnvAnalysisPic;
import com.todaysoft.lims.technical.mybatis.entity.CollectionCnvAnalysisResult;

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
