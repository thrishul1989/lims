package com.todaysoft.lims.technical.mybatis;

import java.util.List;

import com.todaysoft.lims.technical.mybatis.entity.BiologyFamilyAnalysis;
import com.todaysoft.lims.technical.mybatis.parameter.FamilyAnalysisSearcher;

public interface BiologyFamilyAnalysisMapper {
    int deleteByPrimaryKey(String id);

    int insert(BiologyFamilyAnalysis record);

    int insertSelective(BiologyFamilyAnalysis record);

    BiologyFamilyAnalysis selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BiologyFamilyAnalysis record);

    int updateByPrimaryKey(BiologyFamilyAnalysis record);

    List<BiologyFamilyAnalysis> search(FamilyAnalysisSearcher searcher);
}