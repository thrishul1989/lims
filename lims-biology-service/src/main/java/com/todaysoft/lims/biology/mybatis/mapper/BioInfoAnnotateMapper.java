package com.todaysoft.lims.biology.mybatis.mapper;

import java.util.List;

import com.todaysoft.lims.biology.model.request.CallBackSampleModel;
import com.todaysoft.lims.biology.model.request.MaintainDivisionDataRequest;
import com.todaysoft.lims.biology.mybatis.mapper.entity.BioInfoAnnotate;

public interface BioInfoAnnotateMapper
{
    int deleteByPrimaryKey(String id);
    
    int insert(BioInfoAnnotate record);
    
    BioInfoAnnotate selectByPrimaryKey(String id);
    
    int update(BioInfoAnnotate record);
    
    BioInfoAnnotate getAnnotateBySampleCode(String dataCode);
    
    BioInfoAnnotate getAnnotateByTaskId(String taskId);
    
    List<BioInfoAnnotate> getDataForMonitor();
    
    BioInfoAnnotate getByAnalaysisSampleId(String analysisSampleId);
    
    List<BioInfoAnnotate> selectByExample(MaintainDivisionDataRequest request);
    
    void batchUpdateStatus(CallBackSampleModel request);
    
}