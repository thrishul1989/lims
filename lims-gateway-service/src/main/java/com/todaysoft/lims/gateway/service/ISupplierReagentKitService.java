package com.todaysoft.lims.gateway.service;

import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.SupplierReagent;
import com.todaysoft.lims.gateway.model.SupplierReagentKit;
import com.todaysoft.lims.gateway.model.request.ProbeCreateRequest;



public interface ISupplierReagentKitService {

	
	Pagination<SupplierReagentKit> paging(SupplierReagentKit request);
	
	Integer createKit(SupplierReagentKit request);
	
	void delete(Integer id);
	
	void updateKitPrice(SupplierReagentKit supplierReagentKit);
	
}
