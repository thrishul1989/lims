package com.todaysoft.lims.system.service;

import com.todaysoft.lims.system.model.searcher.ProbeSeacher;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.Probe;
import com.todaysoft.lims.system.model.vo.ReagentKit;
import com.todaysoft.lims.system.model.vo.Supplier;

public interface ISupplierService {


	Pagination<Supplier> paging(Supplier searcher,
			int pageNo, int pageSize);
	
	
	void modify(Supplier request);

	Supplier getSupplier(Integer id);

	void delete(Integer id);

	void create(Supplier request);
	
	boolean validate(Supplier request);
	Pagination<Supplier> selectSupplier(Supplier req,int pageNo, int pageSize);
	
}
