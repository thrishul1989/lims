package com.todaysoft.lims.gateway.service.impl;


import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.ProcessTaskModel;
import com.todaysoft.lims.gateway.model.request.ProcessTaskPagingRequest;
import com.todaysoft.lims.gateway.service.IProcessService;
import com.todaysoft.lims.gateway.service.adapter.SampleReceiveAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProcessService implements IProcessService{

    @Autowired
    private SampleReceiveAdapter sampleReceiceAdapter;

    @Override
    public Pagination<ProcessTaskModel> todo(ProcessTaskPagingRequest processTaskPagingRequest)
    {
        return sampleReceiceAdapter.todo(processTaskPagingRequest);
    }
    
    @Override
    public void complete(String taskId)
    {
        sampleReceiceAdapter.complete(taskId);
    }
}
