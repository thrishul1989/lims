package com.todaysoft.lims.system.service;

import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.SupplierReagent;
import com.todaysoft.lims.system.model.vo.SupplierReagentKit;

public interface ISupplierReagentService {

	
	Pagination<SupplierReagent> paging(SupplierReagent searcher,
			int pageNo, int pageSize);
	
	void create(SupplierReagent request);
	
	void delete(Integer id);
	
	void updateReagentPrice(SupplierReagent request);
	
	boolean validate(SupplierReagent reagent);
	
	boolean validate(SupplierReagentKit reagent);
}
