package com.todaysoft.lims.biology.mybatis.mapper;

import com.todaysoft.lims.biology.model.BiologyAnnotationMonitor;

import java.util.List;

public interface BiologyAnnotationMonitorMapper {
    int deleteByPrimaryKey(String id);

    int insert(BiologyAnnotationMonitor record);

    int insertSelective(BiologyAnnotationMonitor record);

    BiologyAnnotationMonitor selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BiologyAnnotationMonitor record);

    int updateByPrimaryKey(BiologyAnnotationMonitor record);

    List<BiologyAnnotationMonitor> searchUncompleteTasks();

    BiologyAnnotationMonitor getByMonitorId(String id);
}