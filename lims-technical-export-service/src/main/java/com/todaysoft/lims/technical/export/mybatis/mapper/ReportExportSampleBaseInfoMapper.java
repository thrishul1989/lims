package com.todaysoft.lims.technical.export.mybatis.mapper;

import java.util.List;

import com.todaysoft.lims.technical.export.mybatis.model.ReportExportSampleBaseInfo;

public interface ReportExportSampleBaseInfoMapper {
    int deleteByPrimaryKey(String id);

    int insert(ReportExportSampleBaseInfo record);

    int insertSelective(ReportExportSampleBaseInfo record);

    ReportExportSampleBaseInfo selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ReportExportSampleBaseInfo record);

    int updateByPrimaryKey(ReportExportSampleBaseInfo record);
    
    List<ReportExportSampleBaseInfo> selectByTaskId(String taskId);
}