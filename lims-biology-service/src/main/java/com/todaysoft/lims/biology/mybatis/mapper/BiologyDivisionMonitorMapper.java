package com.todaysoft.lims.biology.mybatis.mapper;

import com.todaysoft.lims.biology.model.BiologyDivisionMonitor;

import java.util.Date;
import java.util.List;

public interface BiologyDivisionMonitorMapper {
    int deleteByPrimaryKey(String id);

    int insert(BiologyDivisionMonitor record);

    int insertSelective(BiologyDivisionMonitor record);

    BiologyDivisionMonitor selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(BiologyDivisionMonitor record);

    int updateByPrimaryKey(BiologyDivisionMonitor record);

    BiologyDivisionMonitor getByTaskId(String id);

    List<BiologyDivisionMonitor> searchUncompleteTasks(String date);

    List<BiologyDivisionMonitor> getMonitorListByTaskId(String taskId);

    BiologyDivisionMonitor getBySheetId(String sheetId);
}