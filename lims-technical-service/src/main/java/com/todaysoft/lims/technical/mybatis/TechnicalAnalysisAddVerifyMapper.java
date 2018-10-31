package com.todaysoft.lims.technical.mybatis;

import com.todaysoft.lims.technical.model.TechnicalAnalysisAddVerify;

import java.util.List;

public interface TechnicalAnalysisAddVerifyMapper {
    int deleteByPrimaryKey(String id);

    int insert(TechnicalAnalysisAddVerify record);

    int insertSelective(TechnicalAnalysisAddVerify record);

    TechnicalAnalysisAddVerify selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TechnicalAnalysisAddVerify record);

    int updateByPrimaryKey(TechnicalAnalysisAddVerify record);

    List<TechnicalAnalysisAddVerify> getAddVerifyDataByFamilyId(String familyGroupId);

    int deleteByFamilyGroupId(String familyGroupId);
}