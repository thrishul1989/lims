package com.todaysoft.lims.system.service;

import java.util.List;

import com.todaysoft.lims.system.model.vo.KitStorage;
import com.todaysoft.lims.system.model.vo.Pagination;

public interface IKitStorageService {

	
	Pagination<KitStorage> paging(KitStorage searcher,
			int pageNo, int pageSize);
	
	
	void modifyReaction(KitStorage request);
	void delete(Integer id);

	void create(KitStorage request);
	
	
	List<KitStorage> list(KitStorage request);
	
	Integer countByKitId(KitStorage request);
}
