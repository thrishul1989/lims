package com.todaysoft.lims.smm.action;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.smm.entity.Customer;
import com.todaysoft.lims.smm.model.request.CustomerPagingRequest;
import com.todaysoft.lims.smm.service.ICustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private ICustomerService customerService;
	
	@RequestMapping(value = "/paging", method = RequestMethod.POST)
	public Pagination<Customer> paging(@RequestBody CustomerPagingRequest request){
		return customerService.paging(request);
	}
	
	@RequestMapping(value = "/contact_customers")
	 public List<Customer> getContactCustomers(@RequestBody List<Integer> customerIds){
		return customerService.getContactCustomers(customerIds);
	}
}
