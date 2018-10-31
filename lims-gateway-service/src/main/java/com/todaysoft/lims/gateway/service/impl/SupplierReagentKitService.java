package com.todaysoft.lims.gateway.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.SupplierReagentKit;
import com.todaysoft.lims.gateway.service.ISupplierReagentKitService;
import com.todaysoft.lims.gateway.service.adapter.ProbeAdapter;
import com.todaysoft.lims.gateway.service.adapter.SupplierReagentKitAdapter;


@Service
public class SupplierReagentKitService implements ISupplierReagentKitService{

	@Autowired
	private SupplierReagentKitAdapter supplierReagentKitAdapter;
	
	@Override
	public Pagination<SupplierReagentKit> paging(SupplierReagentKit request) {
		return supplierReagentKitAdapter.paging(request);
	}

	@Override
	public Integer createKit(SupplierReagentKit request) {
		return supplierReagentKitAdapter.createKit(request);
	}

	@Override
	public void delete(Integer id) {
		supplierReagentKitAdapter.delete(id);
		
	}

	@Override
	public void updateKitPrice(SupplierReagentKit supplierReagentKit) {
		supplierReagentKitAdapter.updateKitPrice(supplierReagentKit);
		
	}

}
