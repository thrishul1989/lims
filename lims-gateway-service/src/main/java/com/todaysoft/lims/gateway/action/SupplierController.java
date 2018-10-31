package com.todaysoft.lims.gateway.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.Probe;
import com.todaysoft.lims.gateway.model.ReagentKit;
import com.todaysoft.lims.gateway.model.Supplier;
import com.todaysoft.lims.gateway.model.request.ProbeCreateRequest;
import com.todaysoft.lims.gateway.model.request.ProbeListRequest;
import com.todaysoft.lims.gateway.model.request.ProbeModifyRequest;
import com.todaysoft.lims.gateway.service.IProbeService;
import com.todaysoft.lims.gateway.service.ISupplierService;

@RestController
@RequestMapping("/supplier")
public class SupplierController {

	
	 @Autowired
	    private ISupplierService supplierService;
	    
	    @RequestMapping(value = "/paging",method = RequestMethod.POST)
	    public Pagination<Supplier> paging(@RequestBody Supplier request)
	    {

	        return supplierService.paging(request);
	    }
	    
	    @RequestMapping(value = "/list",method = RequestMethod.POST)
	    public List<Supplier> list(@RequestBody Supplier request){
	    	return supplierService.list(request);
	    }
	    
	    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
	    public Supplier get(@PathVariable Integer id)
	    {
	        return supplierService.get(id);
	    }
	    
	    @RequestMapping(value = "/create",method = RequestMethod.POST)
	    public Integer create(@RequestBody Supplier request)
	    {
	        return supplierService.create(request);
	    }
	    
	    @RequestMapping(value = "/modify",method = RequestMethod.POST)
	    public void modify(@RequestBody Supplier request)
	    {
	    	supplierService.modify(request);
	    }
	    
	    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	    public void delete(@PathVariable Integer id)
	    {
	    	supplierService.delete(id);
	    }
	    
	    
	    @RequestMapping(value = "/validate", method = RequestMethod.POST)
	    public boolean validate(@RequestBody Supplier request)
	    {
	    	return supplierService.validate(request);
	    }
	    
	    
	    @RequestMapping("/selectSupplier")
	    public Pagination<Supplier> selectSupplier(@RequestBody Supplier request)
	    {
	        return supplierService.selectSupplier(request);
	    }
	    
}
