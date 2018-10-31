package com.todaysoft.lims.testing.extrasample.service;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.testing.base.entity.ReceivedSample;
import com.todaysoft.lims.testing.base.entity.TestingSample;
import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.base.model.VariableModel;
import com.todaysoft.lims.testing.extrasample.dao.ExtraSampleTaskSearcher;
import com.todaysoft.lims.testing.extrasample.model.ExtraSampleTask;
import com.todaysoft.lims.testing.extrasample.model.ExtraSampleTaskDetails;
import com.todaysoft.lims.testing.extrasample.model.ExtraSampleTaskHandleRequest;

import java.util.List;

public interface IExtraSampleService
{
    Pagination<ExtraSampleTask> paging(ExtraSampleTaskSearcher searcher, int pageNo, int pageSize);

    ExtraSampleTaskDetails getDetails(String id);
    
    void handle(ExtraSampleTaskHandleRequest request, String token,VariableModel model,List<TestingTask> lists);

    ReceivedSample getPrimarySampleByOrderId(String orderId);

    TestingSample getTestingSampleBySampleCode(String sampleCode);
    
}
