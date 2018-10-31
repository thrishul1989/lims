package com.todaysoft.lims.biology.mybatis.mapper;

import com.todaysoft.lims.biology.model.BiologyAnnotationSheet;
import com.todaysoft.lims.biology.model.BiologyDivisionSearchSheet;
import com.todaysoft.lims.biology.model.TestingSheetRequest;

import java.util.List;

public interface BiologyAnnotationSheetMapper {
    int deleteByPrimaryKey(String id);

    int insert(BiologyAnnotationSheet record);

    int insertSelective(BiologyAnnotationSheet record);

    BiologyAnnotationSheet selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BiologyAnnotationSheet record);

    int updateByPrimaryKey(BiologyAnnotationSheet record);

    List<BiologyDivisionSearchSheet> completeSheetPaging(TestingSheetRequest request);

    List<BiologyAnnotationSheet> getSheetListByTaskId(String id);
}