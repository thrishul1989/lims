package com.todaysoft.lims.smm.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.smm.dao.searcher.MaterialSearcher;
import com.todaysoft.lims.smm.entity.Material;
import com.todaysoft.lims.smm.request.MaterialCreateRequest;
import com.todaysoft.lims.smm.service.IMaterialService;

@RestController
@RequestMapping("/smm/material")
public class MaterialController
{
    @Autowired
    private IMaterialService service;
    
    @RequestMapping(value = "/paging", method = RequestMethod.POST)
    public Pagination<Material> paging(@RequestBody MaterialSearcher searcher)
    {
        return service.paging(searcher);
    }
    
    @RequestMapping(value="/sortCreate", method = RequestMethod.POST)
    public void create(@RequestBody MaterialCreateRequest request)
    {
        service.create(request);
    }
    
    @RequestMapping(value="/validate", method = RequestMethod.POST)
    public boolean validate(@RequestBody MaterialSearcher request)
    {        
        return service.validate(request);
    }
    
    @RequestMapping(value="/{id}", method = RequestMethod.GET)
    public Material getById(@PathVariable String id)
    {        
        return service.getById(id);
    }
    
    @RequestMapping(value="/sortModify", method = RequestMethod.POST)
    public void modify(@RequestBody MaterialCreateRequest request)
    {
        service.modify(request);
    }
    
    @RequestMapping(value="/sortDelete/{id}", method = RequestMethod.GET)
    public Integer delete(@PathVariable String id)
    {        
        return service.delete(id);
    }
    
    @RequestMapping("/disable/{id}")
    public void setDisable(@PathVariable String id)
    {
        service.disable(id);
    }
    
    @RequestMapping("/enable/{id}")
    public void setEnable(@PathVariable String id)
    {
        service.enable(id);
    }
    
    @RequestMapping(value = "/getSortList", method = RequestMethod.POST)
    public List<Material> getSortList()
    {
        return service.getSortList();
    }
    
    @RequestMapping(value="/getByName", method = RequestMethod.POST)
    public Material getByName(@RequestBody MaterialSearcher searcher)
    {        
        return service.getByName(searcher);
    }
}
