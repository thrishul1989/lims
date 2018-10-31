package com.todaysoft.lims.technical.mybatis;

import java.util.List;

import com.todaysoft.lims.technical.mybatis.entity.CnvAnalysisMonitor;

public interface CnvAnalysisMonitorMapper
{
    int deleteByPrimaryKey(String id);
    
    int insert(CnvAnalysisMonitor record);
    
    int insertSelective(CnvAnalysisMonitor record);
    
    CnvAnalysisMonitor selectByPrimaryKey(String id);
    
    int updateByPrimaryKeySelective(CnvAnalysisMonitor record);
    
    int updateByPrimaryKey(CnvAnalysisMonitor record);
    
    CnvAnalysisMonitor getBySampleAnalysisId(String params);
    
    List<CnvAnalysisMonitor> getGoingCnvAnalysisList();
    
    CnvAnalysisMonitor getByTaskId(String id);
    
    CnvAnalysisMonitor getCurrentTaskBySampleAnalysisId(String analysisSampleId);
}