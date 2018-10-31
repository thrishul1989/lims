package com.todaysoft.lims.technical.mybatis;

import java.util.List;

import com.todaysoft.lims.technical.mybatis.entity.CnvAnalysisVerify;

public interface CnvAnalysisVerifyMapper {
    int deleteByPrimaryKey(String id);

    int insert(CnvAnalysisVerify record);

    int insertSelective(CnvAnalysisVerify record);

    CnvAnalysisVerify selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CnvAnalysisVerify record);

    int updateByPrimaryKey(CnvAnalysisVerify record);
    
    List<CnvAnalysisVerify> selectByCnvAnalysisResultId(String cnvAnalysisResultId);
}