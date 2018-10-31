package com.todaysoft.lims.sample.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.sample.dao.searcher.ResamplingCancelRecordSearcher;
import com.todaysoft.lims.sample.dao.searcher.ResamplingCancelSolveSearcher;
import com.todaysoft.lims.sample.model.ResamplingCancelRecord;
import com.todaysoft.lims.sample.model.ResamplingCancelRecordDetails;
import com.todaysoft.lims.sample.model.request.ResamplingCancelRecordPagingRequest;
import com.todaysoft.lims.sample.service.IResamplingCancelService;

@RestController
@RequestMapping("/bmm/resampling")
public class ResamplingCancelController
{
    @Autowired
    private IResamplingCancelService service;
    
    @RequestMapping(value = "/cancel_list")
    public Pagination<ResamplingCancelRecord> paging(@RequestBody ResamplingCancelRecordPagingRequest request)
    {
        ResamplingCancelRecordSearcher searcher = new ResamplingCancelRecordSearcher();
        searcher.setOrderCode(request.getOrderCode());
        searcher.setSampleCode(request.getSampleCode());
        searcher.setDataAuthoritySearcher(request.getDataAuthoritySearcher());
        searcher.setProjectManager(request.getProjectManager());
        return service.paging(searcher, request.getPageNo(), request.getPageSize());
    }
    
    @RequestMapping(value = "/cancel_details/{id}")
    public ResamplingCancelRecordDetails details(@PathVariable String id)
    {
        return service.getDetails(id);
    }
    
    @RequestMapping(value = "/cancel_list_solve")
    public Pagination<ResamplingCancelRecord> solvePaging(@RequestBody ResamplingCancelRecordPagingRequest request)
    {
        ResamplingCancelSolveSearcher searcher = new ResamplingCancelSolveSearcher();
        searcher.setOrderCode(request.getOrderCode());
        searcher.setSampleCode(request.getSampleCode());
        searcher.setDataAuthoritySearcher(request.getDataAuthoritySearcher());
        searcher.setProjectManager(request.getProjectManager());

        return service.paging(searcher, request.getPageNo(), request.getPageSize());
    }
    
    @RequestMapping(value = "/cancel_solve_details/{id}")
    public ResamplingCancelRecordDetails solveDetails(@PathVariable String id)
    {
        return service.getSolveDetails(id);
    }
}
