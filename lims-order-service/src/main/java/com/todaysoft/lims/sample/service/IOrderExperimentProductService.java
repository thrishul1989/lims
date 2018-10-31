package com.todaysoft.lims.sample.service;

import java.util.List;

import com.todaysoft.lims.sample.entity.OrderExperimentProduct;
import com.todaysoft.lims.sample.model.request.OrderExperimentProductCreateRequest;
import com.todaysoft.lims.sample.model.request.OrderExperimentProductListRequest;

public interface IOrderExperimentProductService {
	
	List<OrderExperimentProduct> list(OrderExperimentProductListRequest request);
	
	Integer create(OrderExperimentProductCreateRequest request);

	OrderExperimentProduct get(Integer id);
}
