package com.todaysoft.lims.technical.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.technical.entity.SiteplotData;
import com.todaysoft.lims.technical.model.request.SvRequest;
import com.todaysoft.lims.technical.model.response.PagerResponse;
import com.todaysoft.lims.technical.service.ISvService;

@RestController
@RequestMapping("/technicalanaly")
public class SvController
{
    
    @Autowired
    private ISvService service;
    
    @RequestMapping("/sv/pager")
    public PagerResponse<String> pager(@RequestBody SvRequest request)
    {
        PagerResponse<String> pager = service.svPager(request);
        return pager;
    }
    
    @RequestMapping("/sv/regioncoutPager")
    public PagerResponse<SiteplotData> regioncoutPager(@RequestBody SvRequest request)
    {
        PagerResponse<SiteplotData> pager = service.regioncoutPager(request);
        return pager;
    }
}
