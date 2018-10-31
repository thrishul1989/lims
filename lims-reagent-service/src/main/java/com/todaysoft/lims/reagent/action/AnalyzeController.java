package com.todaysoft.lims.reagent.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.reagent.entity.AnalyzeMethod;
import com.todaysoft.lims.reagent.model.request.AnalyzeMethodPagingRequest;
import com.todaysoft.lims.reagent.service.IAnalyzeService;

@RestController
@RequestMapping("/analyzeMethod")
public class AnalyzeController
{
    @Autowired
    private IAnalyzeService service;
    
    @RequestMapping(value = "/paging")
    public Pagination<AnalyzeMethod> paging(@RequestBody AnalyzeMethodPagingRequest request)
    {
        return service.paging(request);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public AnalyzeMethod getMethod(@PathVariable Integer id)
    {
        return service.getMethod(id);
    }
    
    @RequestMapping(value = "/create")
    public Integer create(@RequestBody AnalyzeMethod request)
    {
        return service.create(request);
    }
    
    @RequestMapping(value = "/modify")
    public Integer modify(@RequestBody AnalyzeMethod request)
    {
        return service.modify(request);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Integer id)
    {
        service.delete(id);
    }
    
    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    public boolean validate(@RequestBody AnalyzeMethod request)
    {
        return service.validate(request);
    }
    
    @RequestMapping(value = "/list")
    public List<AnalyzeMethod> list(@RequestBody AnalyzeMethod request)
    {
        return service.list(request);
    }
    
}
