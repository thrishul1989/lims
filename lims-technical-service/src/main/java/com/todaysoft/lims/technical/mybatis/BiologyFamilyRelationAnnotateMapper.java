package com.todaysoft.lims.technical.mybatis;

import com.todaysoft.lims.technical.mybatis.entity.BiologyFamilyRelationAnnotate;

public interface BiologyFamilyRelationAnnotateMapper
{
    int insert(BiologyFamilyRelationAnnotate record);
    
    int insertSelective(BiologyFamilyRelationAnnotate record);
    
    BiologyFamilyRelationAnnotate selectByFamilyId(String familyAnalysisId);
    
    void updateBySelective(BiologyFamilyRelationAnnotate familyAnnotate);
}