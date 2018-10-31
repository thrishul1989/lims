package com.todaysoft.lims.gateway.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.gateway.model.Customer;
import com.todaysoft.lims.gateway.model.Firm;
import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.service.ICustomerService;
import com.todaysoft.lims.gateway.service.adapter.CustomerAdapter;

@Service
public class CustomerService implements ICustomerService {
	
	@Autowired
	private CustomerAdapter customerAdapter;

	@Override
	public Pagination<Customer> paging(Customer request) {
		return customerAdapter.paging(request);
	}
	
	@Override
	public List<Customer> list(Customer request) {
		return customerAdapter.list(request);
	}

	@Override
	public Customer get(Integer id) {
		return customerAdapter.get(id);
	}

	@Override
	public Integer create(Customer request) {
		return customerAdapter.create(request);
	}

	@Override
	public void modify(Customer request) {
		customerAdapter.modify(request);
	}

	@Override
	public void delete(Integer id) {
		customerAdapter.delete(id);
	}

	@Override
	public boolean validate(Customer request) {
		return customerAdapter.validate(request);
	}

	@Override
	public void enableCustomer(Customer request) {
		customerAdapter.enableCustomer(request);
	}

	public List<Customer> getCustomers(List<Integer> customerIds) {
		return customerAdapter.getCustomers(customerIds);
	}
	

}
