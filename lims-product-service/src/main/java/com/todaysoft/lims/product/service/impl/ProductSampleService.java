package com.todaysoft.lims.product.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.product.entity.ProductSample;
import com.todaysoft.lims.product.service.IProductSampleService;

@Service
public class ProductSampleService implements IProductSampleService{
	@Autowired
	private BaseDaoSupport baseDaoSupport;
	
	
	@Override
	@Transactional
	public Integer create(ProductSample request) {
		// TODO Auto-generated method stub
	       baseDaoSupport.insert(request);

	        return Integer.parseInt(request.getId());
	}

}
