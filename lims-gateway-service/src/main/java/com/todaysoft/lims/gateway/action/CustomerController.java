package com.todaysoft.lims.gateway.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.gateway.model.Customer;
import com.todaysoft.lims.gateway.model.Firm;
import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.service.ICustomerService;


@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private ICustomerService customerService;
	
	 @RequestMapping(value = "/paging",method = RequestMethod.POST)
	    public Pagination<Customer> paging(@RequestBody Customer request)
	    {
	        return customerService.paging(request);
	    }
	    
	    @RequestMapping(value = "/list",method = RequestMethod.POST)
	    public List<Customer> list(@RequestBody Customer request){
	    	return customerService.list(request);
	    }
	    
	    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
	    public Customer get(@PathVariable Integer id)
	    {
	        return customerService.get(id);
	    }
	    
	    @RequestMapping(value = "/create",method = RequestMethod.POST)
	    public Integer create(@RequestBody Customer request)
	    {
	        return customerService.create(request);
	    }
	    
	    @RequestMapping(value = "/modify",method = RequestMethod.POST)
	    public void modify(@RequestBody Customer request)
	    {
	    	customerService.modify(request);
	    }
	    
	    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	    public void delete(@PathVariable Integer id)
	    {
	    	customerService.delete(id);
	    }
	    
	    
	    @RequestMapping(value = "/validate", method = RequestMethod.POST)
	    public boolean validate(@RequestBody Customer request)
	    {
	    	return customerService.validate(request);
	    }
	    
	    @RequestMapping(value = "/enableCustomer",method = RequestMethod.POST)
	    public void enableCustomer(@RequestBody Customer request)
	    {
	    	customerService.enableCustomer(request);
	    }
	    
	    
	
}
