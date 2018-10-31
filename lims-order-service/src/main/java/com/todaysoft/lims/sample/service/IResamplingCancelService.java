package com.todaysoft.lims.sample.service;

import java.util.Map;
import java.util.Set;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.sample.dao.searcher.ResamplingCancelRecordSearcher;
import com.todaysoft.lims.sample.dao.searcher.ResamplingCancelSolveSearcher;
import com.todaysoft.lims.sample.model.OrderSampleSimpleModel;
import com.todaysoft.lims.sample.model.ResamplingCancelRecord;
import com.todaysoft.lims.sample.model.ResamplingCancelRecordDetails;

public interface IResamplingCancelService
{
    Pagination<ResamplingCancelRecord> paging(ResamplingCancelRecordSearcher searcher, int pageNo, int pageSize);
    
    Map<String, OrderSampleSimpleModel> mapping(Set<String> samples);
    
    ResamplingCancelRecordDetails getDetails(String id);
    
    Pagination<ResamplingCancelRecord> paging(ResamplingCancelSolveSearcher searcher, int pageNo, int pageSize);
    
    ResamplingCancelRecordDetails getSolveDetails(String id);
}
