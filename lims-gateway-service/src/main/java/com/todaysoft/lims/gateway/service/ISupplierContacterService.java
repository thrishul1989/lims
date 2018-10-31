package com.todaysoft.lims.gateway.service;

import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.Supplier;
import com.todaysoft.lims.gateway.model.SupplierContacter;
import com.todaysoft.lims.gateway.model.request.ProjectModifyRequest;

public interface ISupplierContacterService {

	
	Pagination<SupplierContacter> paging(SupplierContacter request);
	
	Integer create(SupplierContacter request);
	
	 void modify(SupplierContacter request);
	 
	 void delete(Integer id);
	 
}
