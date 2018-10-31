package com.todaysoft.lims.gateway.service;

import java.util.List;

import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.ReagentKit;
import com.todaysoft.lims.gateway.model.Supplier;

public interface ISupplierService {

Pagination<Supplier> paging(Supplier request);
	
	List<Supplier> list(Supplier request);

	Supplier get(Integer id);

	Integer create(Supplier request);

	void modify(Supplier request);

	void delete(Integer id);
	
	boolean validate(Supplier request);
	
	List<Supplier> getSupplierList(List<Integer> ids);
	Pagination<Supplier> selectSupplier(Supplier request);
	
}
