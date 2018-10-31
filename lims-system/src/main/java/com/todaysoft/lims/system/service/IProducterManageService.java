package com.todaysoft.lims.system.service;

import java.util.List;

import com.todaysoft.lims.system.model.searcher.ProducterManageSearcher;
import com.todaysoft.lims.system.model.vo.DataArea;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.ProducterManage;



public interface IProducterManageService {


	Pagination<ProducterManage> paging(ProducterManageSearcher searcher,int pageNo, int pageSize);
	
	void modify(ProducterManage request);

	ProducterManage getProducterManage(String id);

	void delete(String id);
	
	boolean deleteEmail(ProducterManage data);

	void create(ProducterManage request);
	
	boolean validate(ProducterManage request);
	
	DataArea getDataAreaById(String parentId);
	
	List<DataArea> findRoots();
	
	void doSome(ProducterManage data,String flag);
	
}
