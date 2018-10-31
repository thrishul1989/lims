package com.todaysoft.lims.technical.service;

import java.util.List;

import com.todaysoft.lims.technical.model.request.PhenoTypePointsRequest;
import com.todaysoft.lims.technical.mybatis.entity.BioInfoAnnotateHpoMonitor;

public interface IPhenotypeService
{
    
    Integer phenoTypePoints(PhenoTypePointsRequest request);
    
    List<BioInfoAnnotateHpoMonitor> getMonitoringList();
    
}
