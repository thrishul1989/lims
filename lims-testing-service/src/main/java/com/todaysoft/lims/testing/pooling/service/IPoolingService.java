package com.todaysoft.lims.testing.pooling.service;

import java.util.List;

import com.todaysoft.lims.testing.pooling.dao.PoolingAssignableTaskSearcher;
import com.todaysoft.lims.testing.pooling.model.PoolingAssignModel;
import com.todaysoft.lims.testing.pooling.model.PoolingAssignRequest;
import com.todaysoft.lims.testing.pooling.model.PoolingSubmitModel;
import com.todaysoft.lims.testing.pooling.model.PoolingSubmitRequest;
import com.todaysoft.lims.testing.pooling.model.PoolingTask;

public interface IPoolingService
{
    List<PoolingTask> getAssignableList(PoolingAssignableTaskSearcher searcher);
    
    PoolingAssignModel getAssignModel(String id);
    
    boolean isCodeUnique(String code);
    
    void assign(PoolingAssignRequest request, String token);
    
    PoolingSubmitModel getSubmitModel(String id);
    
    void submit(PoolingSubmitRequest request, String token);
}
