package com.todaysoft.lims.sample.service;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.sample.model.ProcessTaskModel;
import com.todaysoft.lims.sample.model.request.ProcessTaskPagingRequest;

public interface IProcessService
{
    Pagination<ProcessTaskModel> todo(ProcessTaskPagingRequest processTaskPagingRequest);
    
    void complete(String taskId);
}
