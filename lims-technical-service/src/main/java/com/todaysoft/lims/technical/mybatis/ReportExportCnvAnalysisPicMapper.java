package com.todaysoft.lims.technical.mybatis;

import java.util.List;

import com.todaysoft.lims.technical.mybatis.entity.ReportExportCnvAnalysisPic;

public interface ReportExportCnvAnalysisPicMapper {
    int deleteByPrimaryKey(String id);

    int insert(ReportExportCnvAnalysisPic record);

    int insertSelective(ReportExportCnvAnalysisPic record);

    ReportExportCnvAnalysisPic selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ReportExportCnvAnalysisPic record);

    int updateByPrimaryKey(ReportExportCnvAnalysisPic record);
    
    List<ReportExportCnvAnalysisPic> selectByTaskId(String taskId);
}