package com.todaysoft.lims.technical.mybatis;

import java.util.List;

import com.todaysoft.lims.technical.mybatis.entity.BioInfoAnnotateHpoMonitor;

public interface BioInfoAnnotateHpoMonitorMapper
{
    int insert(BioInfoAnnotateHpoMonitor record);
    
    int insertSelective(BioInfoAnnotateHpoMonitor record);
    
    BioInfoAnnotateHpoMonitor getByMonitorId(String monitorTaskId);
    
    void updateByPrimaryKey(BioInfoAnnotateHpoMonitor model);
    
    List<BioInfoAnnotateHpoMonitor> getMonitoringList();
    
    BioInfoAnnotateHpoMonitor getBySampleAnalysisId(String analysisSampleId);
}