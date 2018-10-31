package com.todaysoft.lims.system.service.impl;

import java.util.Collections;

import org.springframework.stereotype.Service;

import com.todaysoft.lims.system.model.vo.OrderSampleDetail;
import com.todaysoft.lims.system.service.IOrderSampleService;

@Service
public class OrderSampleService extends RestService implements IOrderSampleService{

	

	@Override
	public OrderSampleDetail getOrderSampleDetail(Integer orderId) {
		   String url = GatewayService.getServiceUrl("//orderSample/orderSampleDetailTrack/{orderId}");
		   return template.getForObject(url, OrderSampleDetail.class, Collections.singletonMap("orderId", orderId));
	}

}
