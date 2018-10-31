package com.todaysoft.lims.biology.mybatis.mapper;

import com.todaysoft.lims.biology.model.BiologyFamilyAnnotationTaskRelation;

import java.util.List;

public interface BiologyFamilyAnnotationTaskRelationMapper {
    int deleteByPrimaryKey(String id);

    int insert(BiologyFamilyAnnotationTaskRelation record);

    int insertSelective(BiologyFamilyAnnotationTaskRelation record);

    BiologyFamilyAnnotationTaskRelation selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BiologyFamilyAnnotationTaskRelation record);

    int updateByPrimaryKey(BiologyFamilyAnnotationTaskRelation record);

    void createRelationList(List<BiologyFamilyAnnotationTaskRelation> relationList);

    List<BiologyFamilyAnnotationTaskRelation> getListByFamilyId(String id);
}