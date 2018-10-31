package com.todaysoft.lims.system.modules.bpm.pooling.service;

import java.util.List;

import com.todaysoft.lims.system.modules.bpm.pooling.model.PoolingAssignModel;
import com.todaysoft.lims.system.modules.bpm.pooling.model.PoolingAssignRequest;
import com.todaysoft.lims.system.modules.bpm.pooling.model.PoolingAssignableTaskSearcher;
import com.todaysoft.lims.system.modules.bpm.pooling.model.PoolingSubmitModel;
import com.todaysoft.lims.system.modules.bpm.pooling.model.PoolingSubmitRequest;
import com.todaysoft.lims.system.modules.bpm.pooling.model.PoolingTask;

public interface IPoolingService
{
    List<PoolingTask> getPoolingAssignableList(PoolingAssignableTaskSearcher searcher);
    
    PoolingAssignModel getPoolingAssignModel(String id);
    
    boolean isCodeUnique(String code);
    
    void assignPooling(PoolingAssignRequest request);
    
    PoolingSubmitModel getPoolingSubmitModel(String id);
    
    void submitPooling(PoolingSubmitRequest request);
}
