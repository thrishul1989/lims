package com.todaysoft.lims.gateway.service;

import java.util.List;

import com.todaysoft.lims.gateway.model.Customer;
import com.todaysoft.lims.gateway.model.Firm;
import com.todaysoft.lims.gateway.model.Pagination;


public interface ICustomerService {
Pagination<Customer> paging(Customer request);
	
	List<Customer> list(Customer request);

	Customer get(Integer id);

	Integer create(Customer request);

	void modify(Customer request);

	void delete(Integer id);
	
	void enableCustomer(Customer request);
	
	boolean validate(Customer request);
}
