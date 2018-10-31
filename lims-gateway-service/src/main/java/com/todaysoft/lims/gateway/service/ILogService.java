package com.todaysoft.lims.gateway.service;

import com.todaysoft.lims.gateway.model.Log;
import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.request.LogCreateRequest;
import com.todaysoft.lims.gateway.model.request.LogPagingRequest;

/**
 * @author admin
 *
 */
public interface ILogService {

    Pagination<Log> paging(LogPagingRequest request);

    Log getLog(Integer id);

    Integer create(LogCreateRequest request);

}
