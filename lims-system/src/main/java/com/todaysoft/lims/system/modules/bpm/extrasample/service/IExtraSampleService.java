package com.todaysoft.lims.system.modules.bpm.extrasample.service;


import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.modules.bpm.extrasample.model.ExtraSampleTask;
import com.todaysoft.lims.system.modules.bpm.extrasample.model.ExtraSampleTaskDetails;
import com.todaysoft.lims.system.modules.bpm.extrasample.model.ExtraSampleTaskHandleRequest;
import com.todaysoft.lims.system.modules.bpm.extrasample.model.ExtraSampleTaskSearcher;

public interface IExtraSampleService
{
    Pagination<ExtraSampleTask> paging(ExtraSampleTaskSearcher searcher, int pageNo, int pageSize);

    ExtraSampleTaskDetails getDetails(String id);

    void handle(ExtraSampleTaskHandleRequest request);

}
