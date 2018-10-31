package com.todaysoft.lims.technical.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.technical.model.request.DataTemplateRequest;
import com.todaysoft.lims.technical.model.response.DataTemplateModel;
import com.todaysoft.lims.technical.mybatis.entity.DataTemplate;
import com.todaysoft.lims.technical.service.IDataTemplateService;
import com.todaysoft.lims.technical.utils.Pagination;

@RestController
@RequestMapping("/technicalanaly/dataTemplate")
public class DataTemplateController
{
    @Autowired
    private IDataTemplateService service;
    
    @RequestMapping("/paging")
    public Pagination<DataTemplate> pager(@RequestBody DataTemplateRequest request)
    {
        return service.pager(request);
    }
    
    @RequestMapping("/create")
    public void create(@RequestBody DataTemplateRequest request)
    {
        service.create(request);
    }
    
    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    public boolean validate(@RequestBody DataTemplateRequest request)
    {
        return service.validate(request);
    }
    
    @RequestMapping("/getById")
    public DataTemplateModel getById(@RequestBody DataTemplateRequest request)
    {
        return service.getById(request.getId());
    }
    
    @RequestMapping("/update")
    public void update(@RequestBody DataTemplateRequest request)
    {
        service.update(request);
    }
    
    @RequestMapping("/delete")
    public void delete(@RequestBody DataTemplateRequest request)
    {
        service.delete(request);
    }
    
    @RequestMapping("/getDataTemplateList")
    public List<DataTemplate> getDataTemplateList(@RequestBody DataTemplateRequest request)
    {
        return service.getDataTemplateList(request);
    }
}
