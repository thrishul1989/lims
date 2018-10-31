package com.todaysoft.lims.gateway.service;

import com.todaysoft.lims.gateway.model.request.OrderTaskCreateRequest;

public interface IOrderTaskService {
	Integer create(OrderTaskCreateRequest request);
}
