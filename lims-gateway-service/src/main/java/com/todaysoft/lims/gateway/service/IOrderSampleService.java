package com.todaysoft.lims.gateway.service;

import java.util.List;

import com.todaysoft.lims.gateway.model.OrderSample;
import com.todaysoft.lims.gateway.model.OrderSampleDetail;
import com.todaysoft.lims.gateway.model.request.OrderSampleCreateRequest;
import com.todaysoft.lims.gateway.model.request.OrderSampleListRequest;

public interface IOrderSampleService {
	
	List<OrderSample> list(OrderSampleListRequest request);
	
	OrderSample get(Integer id);

	Integer create(OrderSampleCreateRequest request);
	
	OrderSampleDetail getOrderSampleDetail(Integer orderId);
}
