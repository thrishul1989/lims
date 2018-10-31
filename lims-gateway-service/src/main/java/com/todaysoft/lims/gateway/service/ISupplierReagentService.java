package com.todaysoft.lims.gateway.service;

import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.SupplierReagent;
import com.todaysoft.lims.gateway.model.SupplierReagentKit;

public interface ISupplierReagentService {

	
Pagination<SupplierReagent> paging(SupplierReagent request);
	
	Integer create(SupplierReagent request);
	
	void delete(Integer id);
	
	 void updateReagentPrice(SupplierReagent supplierReagent);
	 
	 boolean validate(SupplierReagent supplierReagent);
	 
	 boolean validate(SupplierReagentKit supplierReagent);
}
