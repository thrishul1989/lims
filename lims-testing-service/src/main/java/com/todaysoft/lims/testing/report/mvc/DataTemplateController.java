package com.todaysoft.lims.testing.report.mvc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.testing.base.entity.DataTemplate;
import com.todaysoft.lims.testing.base.entity.DataTemplateColumn;
import com.todaysoft.lims.testing.base.entity.TestingMethod;
import com.todaysoft.lims.testing.report.dao.DataTemplateColumnSearcher;
import com.todaysoft.lims.testing.report.dao.DataTemplateSearcher;
import com.todaysoft.lims.testing.report.service.IDataTemplateService;

@RestController
@RequestMapping("/bpm/testing/dataTemplate")
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
    
    @RequestMapping(value = "/dataTemplateList", method = RequestMethod.GET)
    public List<DataTemplate> dataTemplateList(String testingMethodId)
    {
        return service.dataTemplateList(testingMethodId);
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
    
    @RequestMapping(value = "/testingMethodList", method = RequestMethod.GET)
    public List<TestingMethod> testingMethodList()
    {
        return service.getTestingMethodList();
    }
    
    @RequestMapping(value = "/getDataTemplateMapBySheetId/{sheetId}", method = RequestMethod.GET)
    public DataTemplate getDataTemplateMapBySheetId(@PathVariable String sheetId)
    {
        return service.getDataTemplateMapBySheetId(sheetId);
    }
    
    @RequestMapping(value = "/validateTestingMethod/{testingMethodId}", method = RequestMethod.GET)
    public boolean validateTestingMethod(@PathVariable String testingMethodId)
    {
        return service.validateTestingMethod(testingMethodId);
    }

    @RequestMapping(value = "/dataTemplateByMethodSemantic", method = RequestMethod.GET)
    public DataTemplate dataTemplateByMethodSemantic(String semantic)
    {
        return service.dataTemplateByMethodSemantic(semantic);
    }
}
