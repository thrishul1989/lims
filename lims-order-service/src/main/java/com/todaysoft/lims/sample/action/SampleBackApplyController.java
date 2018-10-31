package com.todaysoft.lims.sample.action;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.sample.dao.searcher.SampleBackApplySearcher;
import com.todaysoft.lims.sample.entity.sampleBack.SampleBackApply;
import com.todaysoft.lims.sample.service.ISampleBackApplyService;

@RestController
@RequestMapping("/bmm/sampleBackApply")
public class SampleBackApplyController
{
    @Autowired
    private ISampleBackApplyService sampleBackApplyService;
    
    @RequestMapping(value = "/paging")
    public Pagination<SampleBackApply> paging(@RequestBody SampleBackApplySearcher request)
    {
        Pagination<SampleBackApply> s = sampleBackApplyService.paging(request);
        return s;
    }
    
    @RequestMapping(value = "/getSampleBackApply/{id}", method = RequestMethod.GET)
    public SampleBackApply getSampleBackApply(@PathVariable String id)
    {
        return sampleBackApplyService.getSampleBackApply(id);
    }
    
    @Transactional
    @RequestMapping(value = "/create")
    public void create(@RequestBody SampleBackApply request)
    {
        sampleBackApplyService.create(request);
    }
}
