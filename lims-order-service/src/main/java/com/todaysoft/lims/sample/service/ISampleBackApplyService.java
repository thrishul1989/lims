package com.todaysoft.lims.sample.service;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.sample.dao.searcher.SampleBackApplySearcher;
import com.todaysoft.lims.sample.entity.sampleBack.SampleBackApply;

public interface ISampleBackApplyService
{
    
    Pagination<SampleBackApply> paging(SampleBackApplySearcher request);
    
    SampleBackApply getSampleBackApply(String id);
    
    void create(SampleBackApply sampleBackApply);
}
