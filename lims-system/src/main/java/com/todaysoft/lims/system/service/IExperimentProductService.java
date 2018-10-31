package com.todaysoft.lims.system.service;

import java.util.List;

import com.todaysoft.lims.system.model.vo.ExperimentProduct;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.service.adapter.request.ExperimentProductListRequest;

public interface IExperimentProductService {

	
	 Pagination<ExperimentProduct> paging(ExperimentProduct searcher, int pageNo, int pageSize);
	
	 void create(ExperimentProduct experimentProduct);
	 void modify(ExperimentProduct experimentProduct);
	 
	 ExperimentProduct getExperimentProduct(Integer id);
	 void delete(Integer id);
	
	 List<ExperimentProduct>  list(ExperimentProductListRequest request);
	 
	 boolean checkedName(ExperimentProduct connect);
}
