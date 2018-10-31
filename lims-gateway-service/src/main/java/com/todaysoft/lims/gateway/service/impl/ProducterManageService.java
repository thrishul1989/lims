package com.todaysoft.lims.gateway.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.gateway.model.DataArea;
import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.ProducterManage;
import com.todaysoft.lims.gateway.model.request.ProducterManageRequest;
import com.todaysoft.lims.gateway.service.IProducterManageService;
import com.todaysoft.lims.gateway.service.adapter.ProducterManageAdapter;


@Service
public class ProducterManageService implements IProducterManageService{

	@Autowired
	private ProducterManageAdapter producterManageAdapter;
	
	@Override
	public Pagination<ProducterManage> paging(ProducterManageRequest request) {
		return producterManageAdapter.paging(request);
	}
	
	@Override
	public List<ProducterManage> list(ProducterManage request) {
		return producterManageAdapter.list(request);
	}

	@Override
	public ProducterManage get(String id) {
		return producterManageAdapter.get(id);
	}

	@Override
	public String create(ProducterManage request) {
		return producterManageAdapter.create(request);
	}

	@Override
	public void modify(ProducterManage request) {
		producterManageAdapter.modify(request);
	}

	@Override
	public void delete(String id) {
		producterManageAdapter.delete(id);
	}

	@Override
	public boolean validate(ProducterManage request) {
		
		return producterManageAdapter.validate(request);
	}

	@Override
	public DataArea getDataAreaById(String parentId) {
		
		return producterManageAdapter.getDataAreaById(parentId);
	}

	@Override
	public List<DataArea> findRoots() {
		
		return producterManageAdapter.findRoots();
	}

	@Override
	public boolean deleteEmail(ProducterManage data) {
		
		return producterManageAdapter.deleteEmail(data);
		
	}



}
