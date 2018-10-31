package com.todaysoft.lims.biology.mybatis.mapper;

import com.todaysoft.lims.biology.model.BiologyAnnotationFamilyTask;
import com.todaysoft.lims.biology.model.request.BiologyFamilyAnnotatioListRequest;

import java.util.List;

public interface BiologyAnnotationFamilyTaskMapper {
    int deleteByPrimaryKey(String id);

    int insert(BiologyAnnotationFamilyTask record);

    int insertSelective(BiologyAnnotationFamilyTask record);

    BiologyAnnotationFamilyTask selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BiologyAnnotationFamilyTask record);

    int updateByPrimaryKey(BiologyAnnotationFamilyTask record);

    BiologyAnnotationFamilyTask getByOrderProductMethod(String orderCode, String productCode, String methodId);

    List<BiologyAnnotationFamilyTask> getFamilyTaskSearchList(BiologyFamilyAnnotatioListRequest request);
}