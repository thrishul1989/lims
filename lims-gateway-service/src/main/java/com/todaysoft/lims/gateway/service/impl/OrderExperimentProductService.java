package com.todaysoft.lims.gateway.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.gateway.model.OrderExperimentProduct;
import com.todaysoft.lims.gateway.model.request.OrderExperimentProductCreateRequest;
import com.todaysoft.lims.gateway.model.request.OrderExperimentProductListRequest;
import com.todaysoft.lims.gateway.service.IOrderExperimentProductService;
import com.todaysoft.lims.gateway.service.adapter.OrderExperimentProductAdapter;

@Service
public class OrderExperimentProductService implements
		IOrderExperimentProductService {
	
	@Autowired
	private OrderExperimentProductAdapter adapter;
	
	@Override
	public List<OrderExperimentProduct> list(
			OrderExperimentProductListRequest request) {
		return adapter.list(request);
	}

	@Override
	public Integer create(OrderExperimentProductCreateRequest request) {
		return adapter.create(request);
	}

	@Override
	public OrderExperimentProduct get(Integer id) {
		return adapter.get(id);
	}
}
