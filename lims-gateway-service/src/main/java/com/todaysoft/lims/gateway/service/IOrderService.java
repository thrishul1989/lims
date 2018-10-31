package com.todaysoft.lims.gateway.service;

import java.util.List;

import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.request.order.Order;
import com.todaysoft.lims.gateway.model.request.order.OrderFormRequest;
import com.todaysoft.lims.gateway.model.request.order.OrderSearchRequest;


public interface IOrderService {

	String create(OrderFormRequest request);

	Order getOrderById(String id);

	List<Order> list(OrderSearchRequest request);

	Pagination<Order> paging(OrderSearchRequest request);

	String update(OrderFormRequest request);

	void deleteOrderById(String id);
	
	
	
}
