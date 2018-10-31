package com.todaysoft.lims.gateway.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.request.order.Order;
import com.todaysoft.lims.gateway.model.request.order.OrderFormRequest;
import com.todaysoft.lims.gateway.model.request.order.OrderSearchRequest;
import com.todaysoft.lims.gateway.service.IOrderService;
import com.todaysoft.lims.gateway.service.adapter.OrderAdapter;

@Service
public class OrderService implements IOrderService{

	@Autowired
	private OrderAdapter adapter;
	
	@Override
	public String create(OrderFormRequest request) {
		return adapter.create(request);
	}

	@Override
	public Order getOrderById(String id) {
		return adapter.getOrderById(id);
	}

	@Override
	public List<Order> list(OrderSearchRequest request) {
		return adapter.list(request);
	}

	@Override
	public Pagination<Order> paging(OrderSearchRequest request) {
		return adapter.paging(request);
	}

	@Override
	public String update(OrderFormRequest request) {
		return adapter.update(request);
	}

	@Override
	public void deleteOrderById(String id) {
		adapter.delete(id);
	}

}
