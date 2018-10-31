package com.todaysoft.lims.gateway.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.SupplierContacter;
import com.todaysoft.lims.gateway.service.ISupplierContacterService;
import com.todaysoft.lims.gateway.service.adapter.SupplierContacterAdapter;
@Service
public class SupplierContacterService implements ISupplierContacterService{
	

	@Autowired
	private SupplierContacterAdapter supplierContacterAdapter;

	@Override
	public Pagination<SupplierContacter> paging(SupplierContacter request) {
		return supplierContacterAdapter.paging(request);
	}

	@Override
	public Integer create(SupplierContacter request) {
		return supplierContacterAdapter.create(request);
	}

	@Override
	public void modify(SupplierContacter request) {
		supplierContacterAdapter.modify(request);
		
	}

	@Override
	public void delete(Integer id) {
		supplierContacterAdapter.delete(id);
		
	}

}
