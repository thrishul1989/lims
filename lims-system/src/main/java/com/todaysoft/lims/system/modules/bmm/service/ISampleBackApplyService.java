package com.todaysoft.lims.system.modules.bmm.service;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.system.modules.bmm.model.SampleBackApply;
import com.todaysoft.lims.system.modules.bmm.model.request.SampleBackApplySearcher;

public interface ISampleBackApplyService
{
    Pagination<SampleBackApply> paging(SampleBackApplySearcher searchers);
    
    SampleBackApply getSampleBackApply(String id);
    
    void create(SampleBackApply request);
}
