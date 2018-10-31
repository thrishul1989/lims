package com.todaysoft.lims.biology.mybatis.mapper;

import com.todaysoft.lims.biology.model.BiologyFamilyAnalysisSampleRelation;

import java.util.List;

public interface BiologyFamilyAnalysisSampleRelationMapper {
    int deleteByPrimaryKey(String id);

    int insert(BiologyFamilyAnalysisSampleRelation record);

    int insertSelective(BiologyFamilyAnalysisSampleRelation record);

    BiologyFamilyAnalysisSampleRelation selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BiologyFamilyAnalysisSampleRelation record);

    int updateByPrimaryKey(BiologyFamilyAnalysisSampleRelation record);

    void createRelationList(List<BiologyFamilyAnalysisSampleRelation> relationList);
}