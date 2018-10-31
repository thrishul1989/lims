package com.todaysoft.lims.smm.service.impl;

import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.smm.dao.searcher.CustomerSearcher;
import com.todaysoft.lims.smm.entity.Customer;
import com.todaysoft.lims.smm.model.request.CustomerPagingRequest;
import com.todaysoft.lims.smm.service.ICustomerService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CustomerService implements ICustomerService {
	
	@Autowired
	private BaseDaoSupport baseDaoSupport;

	@Override
	public Pagination<Customer> paging(CustomerPagingRequest request) {
		CustomerSearcher searcher = new CustomerSearcher();
		BeanUtils.copyProperties(request, searcher);
		return baseDaoSupport.find(searcher.toQuery(), request.getPageNo(), request.getPageSize(), Customer.class);
	}
	
	@Override
    public List<Customer> getContactCustomers(List<Integer> customerIds){
    	 if (null == customerIds || customerIds.isEmpty())
         {
             return null;
         }
         
         String hql = "FROM Customer c WHERE c.id IN :customerIds";
         NamedQueryer queryer = new NamedQueryer();
         queryer.setHql(hql);
         queryer.setNames(Arrays.asList("customerIds"));
         queryer.setValues(Arrays.asList((Object)customerIds));
         List<Customer> customerList = baseDaoSupport.find(queryer, Customer.class);
         return customerList;
    }

}
