package com.todaysoft.lims.technical.mybatis;

import java.util.List;

import com.todaysoft.lims.technical.mybatis.entity.ReportExportMutationInfo;

public interface ReportExportMutationInfoMapper {
    int insert(ReportExportMutationInfo record);

    int insertSelective(ReportExportMutationInfo record);
    
    ReportExportMutationInfo selectByPrimaryKey(String id);
    
    List<ReportExportMutationInfo> selectByTaskId(String taskId);
    
    void deleteByPrimaryKey(String id);
    
}