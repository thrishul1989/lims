package com.todaysoft.lims.technical.mybatis;

import java.util.List;

import com.todaysoft.lims.technical.mybatis.entity.BiologyFamilyAnalysisSampleRelation;

public interface BiologyFamilyAnalysisSampleRelationMapper
{
    int deleteByPrimaryKey(String id);
    
    int insert(BiologyFamilyAnalysisSampleRelation record);
    
    int insertSelective(BiologyFamilyAnalysisSampleRelation record);
    
    BiologyFamilyAnalysisSampleRelation selectByPrimaryKey(String id);
    
    int updateByPrimaryKeySelective(BiologyFamilyAnalysisSampleRelation record);
    
    int updateByPrimaryKey(BiologyFamilyAnalysisSampleRelation record);
    
    List<BiologyFamilyAnalysisSampleRelation> selectByFamilyAnalysisId(String analysisSampleId);
    
    List<BiologyFamilyAnalysisSampleRelation> getRelationBySampleId(String id);

    List<BiologyFamilyAnalysisSampleRelation> getRelationByFamilyId(String mongoId);
}