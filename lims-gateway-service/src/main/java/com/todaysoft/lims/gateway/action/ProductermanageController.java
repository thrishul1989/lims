package com.todaysoft.lims.gateway.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.gateway.model.DataArea;
import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.ProducterManage;
import com.todaysoft.lims.gateway.model.request.ProducterManageRequest;
import com.todaysoft.lims.gateway.service.IProducterManageService;

@RestController
@RequestMapping("/producterManage")
public class ProductermanageController {

	
	@Autowired
    private IProducterManageService producterManageService;
    
    @RequestMapping(value = "/paging",method = RequestMethod.POST)
    public Pagination<ProducterManage> paging(@RequestBody ProducterManageRequest request)
    {
    	Pagination<ProducterManage> paging= producterManageService.paging(request);
    	return paging;
    }
    
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public String create(@RequestBody ProducterManage request)
    {
        return producterManageService.create(request);
    }
    
    @RequestMapping(value = "/modify",method = RequestMethod.POST)
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
    
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    public ProducterManage get(@PathVariable String id)
    {
    	return producterManageService.get(id);
    }
    
    @RequestMapping(value = "/deleteEmail",method = RequestMethod.POST)
    public boolean deleteEmail(@RequestBody ProducterManage data)
    {
    	return producterManageService.deleteEmail(data);
    }

}
