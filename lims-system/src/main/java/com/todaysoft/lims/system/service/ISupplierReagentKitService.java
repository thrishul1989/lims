package com.todaysoft.lims.system.service;

import com.todaysoft.lims.system.model.searcher.ProbeSeacher;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.SupplierReagent;
import com.todaysoft.lims.system.model.vo.SupplierReagentKit;

public interface ISupplierReagentKitService {

	
	Pagination<SupplierReagentKit> paging(SupplierReagentKit searcher,
			int pageNo, int pageSize);
	
	void createKit(SupplierReagentKit request);
	
	void delete(Integer id);
	
	void updateKitPrice(SupplierReagentKit request);
	
	
}
