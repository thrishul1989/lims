package com.todaysoft.lims.gateway.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.gateway.model.ExperimentProduct;
import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.request.ExperimentProductCreateRequest;
import com.todaysoft.lims.gateway.model.request.ExperimentProductListRequest;
import com.todaysoft.lims.gateway.model.request.ExperimentProductModifyRequest;
import com.todaysoft.lims.gateway.model.request.ExperimentProductPagingRequest;
import com.todaysoft.lims.gateway.service.IExperimentProductService;
import com.todaysoft.lims.gateway.service.adapter.ExperimentProductAdapter;

@Service
public class ExperimentProductService implements IExperimentProductService {
	@Autowired
	private ExperimentProductAdapter experimentProductAdapter;
	
	@Override
	public Pagination<ExperimentProduct> paging(
			ExperimentProductPagingRequest request) {
		return experimentProductAdapter.paging(request);
	}
	
	@Override
	public List<ExperimentProduct> list(ExperimentProductListRequest request) {
		return experimentProductAdapter.list(request);
	}

	@Override
	public ExperimentProduct getExperimentProduct(Integer id) {
		return experimentProductAdapter.getExperimentProduct(id);
	}

	@Override
	public Integer create(ExperimentProductCreateRequest request) {
		return experimentProductAdapter.create(request);
	}

	@Override
	public void modify(ExperimentProductModifyRequest request) {
		experimentProductAdapter.modify(request);
	}

	@Override
	public void delete(Integer id) {
		experimentProductAdapter.delete(id);
	}

	@Override
	public boolean checkedName(ExperimentProductPagingRequest connect) {
		
		return experimentProductAdapter.checkedName(connect);
	}

}
