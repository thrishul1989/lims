package com.todaysoft.lims.technical.export.mybatis.mapper;

import java.util.List;

import com.todaysoft.lims.technical.export.mybatis.model.CnvAnalysisPic;

public interface CnvAnalysisPicMapper {
	int deleteByPrimaryKey(String id);

	int insert(CnvAnalysisPic record);

	int insertSelective(CnvAnalysisPic record);

	CnvAnalysisPic selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(CnvAnalysisPic record);

	int updateByPrimaryKey(CnvAnalysisPic record);

	List<CnvAnalysisPic> getBySampleTestId(String sampleAnalysisId);

	List<CnvAnalysisPic> getDoctorCollectionByAnalysisSampleId(String analysisSampleId,String doctorId);
	
	List<CnvAnalysisPic> getByIds(List<String> ids);

}