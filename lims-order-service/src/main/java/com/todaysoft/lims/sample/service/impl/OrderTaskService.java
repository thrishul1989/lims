package com.todaysoft.lims.sample.service.impl;

import javax.transaction.Transactional;

import com.todaysoft.lims.persist.BaseDaoSupport;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.sample.entity.OrderTask;
import com.todaysoft.lims.sample.model.request.OrderTaskCreateRequest;
import com.todaysoft.lims.sample.service.IOrderTaskService;

@Service
public class OrderTaskService implements IOrderTaskService {

	@Autowired
	private BaseDaoSupport baseDaoSupport;
	
	@Override
	@Transactional
	public Integer create(OrderTaskCreateRequest request) {
		OrderTask entity = new OrderTask();
		BeanUtils.copyProperties(request, entity);
		baseDaoSupport.insert(entity);
		return entity.getId();
	}

}
