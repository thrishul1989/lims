package com.todaysoft.lims.system.service;

import com.todaysoft.lims.system.model.searcher.ProbeSeacher;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.Supplier;
import com.todaysoft.lims.system.model.vo.SupplierContacter;

public interface ISupplierContacterService {

	

	Pagination<SupplierContacter> paging(SupplierContacter searcher,
			int pageNo, int pageSize);
	
	void create(SupplierContacter request);
	
	void modify(SupplierContacter request);
	
	void delete(Integer id);
}
