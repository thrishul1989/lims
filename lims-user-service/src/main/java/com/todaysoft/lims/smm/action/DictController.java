package com.todaysoft.lims.smm.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.smm.entity.Dict;
import com.todaysoft.lims.smm.model.request.DictPagingRequest;
import com.todaysoft.lims.smm.model.response.DictAdapter;
import com.todaysoft.lims.smm.service.IDictService;

@RestController
@RequestMapping("/smm/dict")
public class DictController
{
    @Autowired
    private IDictService service;
    
    @RequestMapping(value = "/paging")
    public Pagination<DictAdapter> paging(@RequestBody DictPagingRequest request)
    {
        return service.paging(request);
    }
    
    @RequestMapping("/{category}/entries")
    public List<DictAdapter> getEntries(@PathVariable String category)
    {
        return service.getEntries(category);
    }
    
    @RequestMapping("/{category}/entries/{value}")
    public DictAdapter getEntry(@PathVariable String category, @PathVariable String value)
    {
        if (value.indexOf("_") != -1)
        {
            value = value.replace("_", "/");
        }
        return service.getEntry(category, value);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public DictAdapter getDict(@PathVariable String id)
    {
        return service.get(id);
    }
    
    @RequestMapping(value = "/getDict/{category}/{value}", method = RequestMethod.GET)
    public DictAdapter getDict(@PathVariable String category, @PathVariable String value)
    {
        return service.getDict(category, value);
    }
    
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public void modify(@RequestBody Dict request)
    {
        service.modify(request);
    }
    
    @RequestMapping(value = "/getDictByText", method = RequestMethod.GET)
    public DictAdapter getDictByText(String category, String text)
    {
        return service.getDictByText(category, text);
    }
}