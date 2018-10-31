package com.todaysoft.lims.gateway.service;

import java.util.List;

import com.todaysoft.lims.gateway.model.KitStorage;
import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.request.ProbeCreateRequest;
import com.todaysoft.lims.gateway.model.request.ProbeModifyRequest;


public interface IKitStorageService {

	
	Pagination<KitStorage> paging(KitStorage request);
	KitStorage get(Integer id);
	void modifyReaction(KitStorage request);
	Integer create(KitStorage request);
	void delete(Integer id);
	
	List<KitStorage> list(KitStorage request);
	Integer countByKitId(KitStorage request);
}
