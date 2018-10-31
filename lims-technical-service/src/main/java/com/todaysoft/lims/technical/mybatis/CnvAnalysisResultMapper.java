package com.todaysoft.lims.technical.mybatis;


import java.util.List;
import java.util.Map;

import com.todaysoft.lims.technical.mybatis.entity.CnvAnalysisResult;

public interface CnvAnalysisResultMapper {
    int deleteByPrimaryKey(String id);

    int insert(CnvAnalysisResult record);

    int insertSelective(CnvAnalysisResult record);

    CnvAnalysisResult selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CnvAnalysisResult record);

    int updateByPrimaryKey(CnvAnalysisResult record);

    List<CnvAnalysisResult> getListBySampleTestId(String sampleTestId);

	List<CnvAnalysisResult> getDoctorCollectionByAnalysisSampleId(String analysisSampleId, String doctorId);
	
    List<CnvAnalysisResult> getListBySampleTestIdAndCnvAnalysisPicId(String analysisSampleId,String picId);
    
    List<CnvAnalysisResult> getListByCnvAnalysisPicId(String picId);
    
    List<CnvAnalysisResult> getByIdsAndPicId(Map<String, Object> params);
}