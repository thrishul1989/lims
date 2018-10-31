package com.todaysoft.lims.system.service;


import com.todaysoft.lims.system.model.searcher.LogSearcher;
import com.todaysoft.lims.system.model.vo.Log;
import com.todaysoft.lims.system.model.vo.Pagination;

/**
 * @author admin
 *
 */
public interface ILogService{

	Pagination<Log> paging(LogSearcher searcher, int pageNo, int pageSize);
	void create(Log data);
}
