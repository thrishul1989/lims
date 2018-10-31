package com.todaysoft.lims.report.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.report.dao.searcher.DataTemplateColumnSearcher;
import com.todaysoft.lims.report.dao.searcher.DataTemplateSearcher;
import com.todaysoft.lims.report.entity.DataTemplate;
import com.todaysoft.lims.report.entity.DataTemplateColumn;
import com.todaysoft.lims.report.service.IDataTemplateService;

@RestController
@RequestMapping("/report/dataTemplate")
public class DataTemplateController
{
    
    @Autowired
    private IDataTemplateService service;
    
    @RequestMapping(value = "/paging", method = RequestMethod.POST)
    public Pagination<DataTemplate> paging(@RequestBody DataTemplateSearcher searcher)
    {
        return service.paging(searcher);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public DataTemplate get(@PathVariable String id)
    {
        return service.get(id);
    }
    
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public void create(@RequestBody DataTemplate request)
    {
        service.create(request);
    }
    
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public void modify(@RequestBody DataTemplate request)
    {
        service.modify(request);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable String id)
    {
        service.delete(id);
    }
    
    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    public boolean validate(@RequestBody DataTemplate request)
    {
        return service.validate(request);
    }
    
    @RequestMapping(value = "/selectDataTemplate", method = RequestMethod.POST)
    public List<DataTemplate> dataTemplateList(@RequestBody DataTemplateSearcher searcher)
    {
        return service.dataTemplateList(searcher);
    }
    
    @RequestMapping(value = "/dataTemplateColumnList", method = RequestMethod.POST)
    public List<DataTemplateColumn> dataTemplateColumnList(@RequestBody DataTemplateColumnSearcher searcher)
    {
        return service.dataTemplateColumnList(searcher);
    }
    
    @RequestMapping(value = "/dataTemplateColumn/{id}", method = RequestMethod.GET)
    public DataTemplateColumn getDataTemplateColumn(@PathVariable String id)
    {
        return service.getDataTemplateColumn(id);
    }
}
