package com.todaysoft.lims.reagent.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.reagent.entity.ProducterManage;
import com.todaysoft.lims.reagent.model.request.ProducterManageRequest;
import com.todaysoft.lims.reagent.service.IProducterManageService;

@RestController
@RequestMapping("/producterManage")
public class ProducterManageController
{
    
    @Autowired
    private IProducterManageService producterManageService;
    
    @RequestMapping(value = "/paging", method = RequestMethod.POST)
    public Pagination<ProducterManage> paging(@RequestBody ProducterManageRequest request)
    {
        return producterManageService.paging(request);
    }
    
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ProducterManage get(@PathVariable String id)
    {
        return producterManageService.get(id);
    }
    
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@RequestBody ProducterManage request)
    {
        return producterManageService.create(request);
    }
    
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public void modify(@RequestBody ProducterManage request)
    {
        producterManageService.modify(request);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable String id)
    {
        producterManageService.delete(id);
    }
    
    @RequestMapping(value = "/validate", method = RequestMethod.POST)
    public boolean validate(@RequestBody ProducterManage request)
    {
        return producterManageService.validate(request);
    }
    
    @RequestMapping(value = "/deleteEmail", method = RequestMethod.POST)
    public boolean deleteEmail(@RequestBody ProducterManage data)
    {
        return producterManageService.deleteEmail(data);
    }
    
}
