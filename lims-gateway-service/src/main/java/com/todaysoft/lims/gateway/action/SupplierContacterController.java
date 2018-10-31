package com.todaysoft.lims.gateway.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.Supplier;
import com.todaysoft.lims.gateway.model.SupplierContacter;
import com.todaysoft.lims.gateway.model.request.ProbeCreateRequest;
import com.todaysoft.lims.gateway.model.request.ProbeModifyRequest;
import com.todaysoft.lims.gateway.service.ISupplierContacterService;



@RestController
@RequestMapping("/supplierContacter")
public class SupplierContacterController {

	
	@Autowired
    private ISupplierContacterService supplierContacterService;
    
    @RequestMapping(value = "/paging",method = RequestMethod.POST)
    public Pagination<SupplierContacter> paging(@RequestBody SupplierContacter request)
    {
    	Pagination<SupplierContacter> paging= supplierContacterService.paging(request);
    	return paging;
    }
    
    
    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public Integer create(@RequestBody SupplierContacter request)
    {
        return supplierContacterService.create(request);
    }
    
    @RequestMapping(value = "/modify",method = RequestMethod.POST)
    public void modify(@RequestBody SupplierContacter request)
    {
    	supplierContacterService.modify(request);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Integer id)
    {
    	supplierContacterService.delete(id);
    }
    
    
    
}
