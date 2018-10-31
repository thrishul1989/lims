package com.todaysoft.lims.gateway.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.gateway.model.Log;
import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.request.LogCreateRequest;
import com.todaysoft.lims.gateway.model.request.LogPagingRequest;
import com.todaysoft.lims.gateway.service.ILogService;
import com.todaysoft.lims.gateway.service.adapter.LogAdapter;

/**
 * @author admin
 *
 */
@Service
public class LogService implements ILogService {

	@Autowired
    private LogAdapter logAdapter;
	@Override
	public Pagination<Log> paging(LogPagingRequest request) {
		// TODO Auto-generated method stub
		return logAdapter.paging(request);
	}

	@Override
	public Log getLog(Integer id) {
		// TODO Auto-generated method stub
		return new Log();
	}

	@Override
	public Integer create(LogCreateRequest request) {
		// TODO Auto-generated method stub
		return logAdapter.create(request);
	}

	

}
