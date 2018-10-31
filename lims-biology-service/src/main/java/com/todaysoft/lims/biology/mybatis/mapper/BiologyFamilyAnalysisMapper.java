package com.todaysoft.lims.biology.mybatis.mapper;

import com.todaysoft.lims.biology.model.BiologyFamilyAnalysis;

public interface BiologyFamilyAnalysisMapper {
    int deleteByPrimaryKey(String id);

    int insert(BiologyFamilyAnalysis record);

    int insertSelective(BiologyFamilyAnalysis record);

    BiologyFamilyAnalysis selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BiologyFamilyAnalysis record);

    int updateByPrimaryKey(BiologyFamilyAnalysis record);

    BiologyFamilyAnalysis getByTaskId(String taskId);
}