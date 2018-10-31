package com.todaysoft.lims.gateway.action;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.gateway.model.Log;
import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.request.LogCreateRequest;
import com.todaysoft.lims.gateway.model.request.LogPagingRequest;
import com.todaysoft.lims.gateway.service.ILogService;

/**
 * @author admin
 *
 */
@RestController
@RequestMapping("/log")
public class LogController {

	@Autowired
	private ILogService service;

    @RequestMapping("/paging")
    public Pagination<Log> paging(@RequestBody LogPagingRequest request)
    {
        return service.paging(request);
    }

    @RequestMapping("/{id}")
    public Log getLog(@PathVariable Integer id)
    {
        return service.getLog(id);
    }

    @RequestMapping(value = "/create")
    public String create(@RequestBody LogCreateRequest request)
    {
        Integer id = service.create(request);
        return String.valueOf(id);
    }

}
