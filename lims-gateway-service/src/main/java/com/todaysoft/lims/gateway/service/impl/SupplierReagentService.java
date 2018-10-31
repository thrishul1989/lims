package com.todaysoft.lims.gateway.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.SupplierReagent;
import com.todaysoft.lims.gateway.model.SupplierReagentKit;
import com.todaysoft.lims.gateway.service.ISupplierReagentKitService;
import com.todaysoft.lims.gateway.service.ISupplierReagentService;
import com.todaysoft.lims.gateway.service.adapter.SupplierReagentAdapter;
import com.todaysoft.lims.gateway.service.adapter.SupplierReagentKitAdapter;

@Service
public class SupplierReagentService implements ISupplierReagentService{

	
	@Autowired
	private SupplierReagentAdapter supplierReagentAdapter;
	
	@Override
	public Pagination<SupplierReagent> paging(SupplierReagent request) {
		return supplierReagentAdapter.paging(request);
	}

	@Override
	public Integer create(SupplierReagent request) {
		return supplierReagentAdapter.create(request);
	}

	@Override
	public void delete(Integer id) {
		supplierReagentAdapter.delete(id);
		
	}

	@Override
	public void updateReagentPrice(SupplierReagent supplierReagent) {
		supplierReagentAdapter.updateReagentPrice(supplierReagent);
		
	}

	@Override
	public boolean validate(SupplierReagent supplierReagent) {
		
		return supplierReagentAdapter.validate(supplierReagent);
	}
	
	@Override
	public boolean validate(SupplierReagentKit supplierReagent) {
		
		return supplierReagentAdapter.validate(supplierReagent);
	}
	
	
}
