package com.todaysoft.lims.gateway.service;

import java.util.List;

import com.todaysoft.lims.gateway.model.ExperimentProduct;
import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.request.ExperimentProductCreateRequest;
import com.todaysoft.lims.gateway.model.request.ExperimentProductListRequest;
import com.todaysoft.lims.gateway.model.request.ExperimentProductModifyRequest;
import com.todaysoft.lims.gateway.model.request.ExperimentProductPagingRequest;
import com.todaysoft.lims.gateway.model.request.PhenotypeRequest;

public interface IExperimentProductService {
	Pagination<ExperimentProduct> paging(ExperimentProductPagingRequest request);
	
	List<ExperimentProduct> list(ExperimentProductListRequest request);

	ExperimentProduct getExperimentProduct(Integer id);

	Integer create(ExperimentProductCreateRequest request);

	void modify(ExperimentProductModifyRequest request);

	void delete(Integer id);
	
	boolean checkedName(ExperimentProductPagingRequest connect);
}
