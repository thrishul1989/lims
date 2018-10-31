package com.todaysoft.lims.gateway.service;


import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.ProcessTaskModel;
import com.todaysoft.lims.gateway.model.request.ProcessTaskPagingRequest;

public interface IProcessService
{
    Pagination<ProcessTaskModel> todo(ProcessTaskPagingRequest processTaskPagingRequest);
    
    void complete(String taskId);
}
