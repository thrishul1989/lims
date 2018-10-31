package com.todaysoft.lims.technical.export.mybatis.mapper;

import java.util.List;

import com.todaysoft.lims.technical.export.mybatis.model.ReportExportCapcnvResult;

public interface ReportExportCapcnvResultMapper {
    int deleteByPrimaryKey(String id);

    int insert(ReportExportCapcnvResult record);

    int insertSelective(ReportExportCapcnvResult record);

    ReportExportCapcnvResult selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ReportExportCapcnvResult record);

    int updateByPrimaryKey(ReportExportCapcnvResult record);
    
    List<ReportExportCapcnvResult> selectByTaskId(String taskId);
}