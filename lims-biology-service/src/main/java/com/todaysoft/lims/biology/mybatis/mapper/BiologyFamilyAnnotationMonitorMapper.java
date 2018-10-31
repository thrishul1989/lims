package com.todaysoft.lims.biology.mybatis.mapper;

import com.todaysoft.lims.biology.model.BiologyFamilyAnnotationMonitor;

import java.util.List;

public interface BiologyFamilyAnnotationMonitorMapper {
    int deleteByPrimaryKey(String id);

    int insert(BiologyFamilyAnnotationMonitor record);

    int insertSelective(BiologyFamilyAnnotationMonitor record);

    BiologyFamilyAnnotationMonitor selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BiologyFamilyAnnotationMonitor record);

    int updateByPrimaryKey(BiologyFamilyAnnotationMonitor record);

    BiologyFamilyAnnotationMonitor getByMonitorId(String id);

    List<BiologyFamilyAnnotationMonitor> searchUncompleteTasks();
}