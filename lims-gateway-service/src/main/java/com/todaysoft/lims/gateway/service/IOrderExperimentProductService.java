package com.todaysoft.lims.gateway.service;

import java.util.List;

import com.todaysoft.lims.gateway.model.OrderExperimentProduct;
import com.todaysoft.lims.gateway.model.request.OrderExperimentProductCreateRequest;
import com.todaysoft.lims.gateway.model.request.OrderExperimentProductListRequest;

public interface IOrderExperimentProductService {
	
	List<OrderExperimentProduct> list(OrderExperimentProductListRequest request);
	
	Integer create(OrderExperimentProductCreateRequest request);

	OrderExperimentProduct get(Integer id);
}
