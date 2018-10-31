package com.todaysoft.lims.gateway.service;

import java.util.List;

import com.todaysoft.lims.gateway.model.DataArea;
import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.ProducterManage;
import com.todaysoft.lims.gateway.model.request.ProducterManageRequest;

public interface IProducterManageService {

Pagination<ProducterManage> paging(ProducterManageRequest request);
	
	List<ProducterManage> list(ProducterManage request);

	ProducterManage get(String id);

	String create(ProducterManage request);

	void modify(ProducterManage request);

	void delete(String id);
	
	boolean deleteEmail(ProducterManage data);
	
	boolean validate(ProducterManage request);
	
	DataArea getDataAreaById(String parentId);
	
	List<DataArea> findRoots();
	
	
}
