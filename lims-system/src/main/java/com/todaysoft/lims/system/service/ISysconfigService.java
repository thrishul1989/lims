package com.todaysoft.lims.system.service;

import com.todaysoft.lims.system.model.searcher.ProbeSeacher;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.Probe;
import com.todaysoft.lims.system.model.vo.Sysconfig;

public interface ISysconfigService {

	
	Pagination<Sysconfig> paging(Sysconfig searcher,
			int pageNo, int pageSize);
	
	
	void modify(Sysconfig request);

	Sysconfig getSysconfig(Integer id);

	void delete(Integer id);

	void create(Sysconfig request);
	
	boolean validate(Sysconfig request);
	
	
}
