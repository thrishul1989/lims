package com.todaysoft.lims.gateway.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.gateway.model.request.OrderTaskCreateRequest;
import com.todaysoft.lims.gateway.service.IOrderTaskService;

@RestController
@RequestMapping("/orderTask")
public class OrderTaskController {

	@Autowired
	private IOrderTaskService service;
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public Integer create(@RequestBody OrderTaskCreateRequest request){
		return service.create(request);
	}
}
