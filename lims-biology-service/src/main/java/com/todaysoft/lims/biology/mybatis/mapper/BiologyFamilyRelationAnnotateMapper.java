package com.todaysoft.lims.biology.mybatis.mapper;

import com.todaysoft.lims.biology.model.BiologyFamilyRelationAnnotate;

public interface BiologyFamilyRelationAnnotateMapper {
    int insert(BiologyFamilyRelationAnnotate record);

    int insertSelective(BiologyFamilyRelationAnnotate record);

    BiologyFamilyRelationAnnotate getBiologyFamilyRelationAnnotateByFamilyId(String id);

    void updateByPrimaryKey(BiologyFamilyRelationAnnotate familyRelationAnnotate);
}