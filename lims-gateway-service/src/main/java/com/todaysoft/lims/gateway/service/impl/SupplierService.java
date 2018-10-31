package com.todaysoft.lims.gateway.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.Supplier;
import com.todaysoft.lims.gateway.service.ISupplierService;

import com.todaysoft.lims.gateway.service.adapter.SupplierAdapter;

@Service
public class SupplierService implements ISupplierService{

	@Autowired
	private SupplierAdapter supplierAdapter;
	
	@Override
	public Pagination<Supplier> paging(Supplier request) {
		return supplierAdapter.paging(request);
	}
	
	@Override
	public List<Supplier> list(Supplier request) {
		return supplierAdapter.list(request);
	}

	@Override
	public Supplier get(Integer id) {
		return supplierAdapter.get(id);
	}

	@Override
	public Integer create(Supplier request) {
		return supplierAdapter.create(request);
	}

	@Override
	public void modify(Supplier request) {
		supplierAdapter.modify(request);
	}

	@Override
	public void delete(Integer id) {
		supplierAdapter.delete(id);
	}

	@Override
	public boolean validate(Supplier request) {
		// TODO Auto-generated method stub
		return supplierAdapter.validate(request);
	}

	@Override
	public List<Supplier> getSupplierList(List<Integer> ids) {
		// TODO Auto-generated method stub
		return supplierAdapter.getSupplierList(ids);
	}

	@Override
	public Pagination<Supplier> selectSupplier(Supplier request) {
		return supplierAdapter.selectSupplier(request);
	}

}
