package com.todaysoft.lims.sample.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import com.todaysoft.lims.persist.BaseDaoSupport;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.sample.dao.searcher.OrderExperimentProductSearcher;
import com.todaysoft.lims.sample.entity.OrderExperimentProduct;
import com.todaysoft.lims.sample.model.request.OrderExperimentProductCreateRequest;
import com.todaysoft.lims.sample.model.request.OrderExperimentProductListRequest;
import com.todaysoft.lims.sample.service.IOrderExperimentProductService;

@Service
public class OrderExperimentProductService implements
		IOrderExperimentProductService {
	
	@Autowired
	private BaseDaoSupport baseDaoSupport;

	@Override
	@Transactional
	public Integer create(OrderExperimentProductCreateRequest request) {
		OrderExperimentProduct entity = new OrderExperimentProduct();
		BeanUtils.copyProperties(request, entity);
		baseDaoSupport.insert(entity);
		return entity.getId();
	}

	@Override
	public OrderExperimentProduct get(Integer id) {
		return baseDaoSupport.get(OrderExperimentProduct.class, id);
	}

	@Override
	public List<OrderExperimentProduct> list(
			OrderExperimentProductListRequest request) {
		OrderExperimentProductSearcher searcher = new OrderExperimentProductSearcher();
		BeanUtils.copyProperties(request, searcher);
		return baseDaoSupport.find(searcher);
	}

}
