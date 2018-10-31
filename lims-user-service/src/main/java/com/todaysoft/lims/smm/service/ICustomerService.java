package com.todaysoft.lims.smm.service;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.smm.entity.Customer;
import com.todaysoft.lims.smm.model.request.CustomerPagingRequest;

import java.util.List;

public interface ICustomerService {
	Pagination<Customer> paging(CustomerPagingRequest request);

	List<Customer> getContactCustomers(List<Integer> customerIds);
}
