package com.todaysoft.lims.system.modules.bmm.service;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.system.modules.bmm.model.ResamplingCancelRecord;
import com.todaysoft.lims.system.modules.bmm.model.ResamplingCancelRecordDetails;
import com.todaysoft.lims.system.modules.bmm.model.ResamplingCancelRecordHandleForm;
import com.todaysoft.lims.system.modules.bmm.model.ResamplingCancelRecordSearcher;

public interface IResamplingCancelService
{
    Pagination<ResamplingCancelRecord> paging(ResamplingCancelRecordSearcher searcher, int pageNo, int pageSize);
    
    ResamplingCancelRecordDetails getDetails(String id);
    
    void handle(ResamplingCancelRecordHandleForm data);
    
    Pagination<ResamplingCancelRecord> solvePaging(ResamplingCancelRecordSearcher searcher, int pageNo, int pageSize);
    
    ResamplingCancelRecordDetails getSolveDetails(String id);
}
