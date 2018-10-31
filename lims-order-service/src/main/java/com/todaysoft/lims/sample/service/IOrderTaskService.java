package com.todaysoft.lims.sample.service;

import com.todaysoft.lims.sample.model.request.OrderTaskCreateRequest;

public interface IOrderTaskService {
	Integer create(OrderTaskCreateRequest request);
}
