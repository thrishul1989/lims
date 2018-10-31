package com.todaysoft.lims.report.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.report.dao.searcher.ReportTemplateSearcher;
import com.todaysoft.lims.report.entity.ReportTemplate;
import com.todaysoft.lims.report.service.IReportTemplateService;

@RestController
@RequestMapping("/report/reportTemplate")
public class ReportTemplateController
{
    
    @Autowired
    private IReportTemplateService service;
    
    @RequestMapping(value = "/paging", method = RequestMethod.POST)
    public Pagination<ReportTemplate> paging(@RequestBody ReportTemplateSearcher searcher)
    {
        return service.paging(searcher);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ReportTemplate get(@PathVariable String id)
    {
        return service.get(id);
    }
    
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public void create(@RequestBody ReportTemplate request)
    {
        service.create(request);
    }
    
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public void modify(@RequestBody ReportTemplate request)
    {
        service.modify(request);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable String id)
    {
        service.delete(id);
    }
    
    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    public boolean validate(@RequestBody ReportTemplate request)
    {
        return service.validate(request);
    }
    
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public List<ReportTemplate> list(@RequestBody ReportTemplateSearcher searcher)
    {
        return service.list(searcher);
    }
}
