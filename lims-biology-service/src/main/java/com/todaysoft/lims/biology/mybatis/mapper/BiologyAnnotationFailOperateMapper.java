package com.todaysoft.lims.biology.mybatis.mapper;

import com.todaysoft.lims.biology.model.BiologyAnnotationFailOperate;

import java.util.List;

public interface BiologyAnnotationFailOperateMapper {
    int deleteByPrimaryKey(String id);

    int insert(BiologyAnnotationFailOperate record);

    int insertSelective(BiologyAnnotationFailOperate record);

    BiologyAnnotationFailOperate selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BiologyAnnotationFailOperate record);

    int updateByPrimaryKey(BiologyAnnotationFailOperate record);

    List<BiologyAnnotationFailOperate> getListByTaskId(String taskId);
}