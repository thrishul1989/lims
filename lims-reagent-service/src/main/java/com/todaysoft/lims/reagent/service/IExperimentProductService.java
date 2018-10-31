package com.todaysoft.lims.reagent.service;

import java.util.List;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.reagent.entity.ExperimentProduct;
import com.todaysoft.lims.reagent.model.request.ExperimentProductCreateRequest;
import com.todaysoft.lims.reagent.model.request.ExperimentProductListRequest;
import com.todaysoft.lims.reagent.model.request.ExperimentProductModifyRequest;
import com.todaysoft.lims.reagent.model.request.ExperimentProductPagingRequest;

public interface IExperimentProductService
{
    Pagination<ExperimentProduct> paging(ExperimentProductPagingRequest request);
    
    List<ExperimentProduct> list(ExperimentProductListRequest request);
    
    ExperimentProduct getExperimentProduct(Integer id);
    
    Integer create(ExperimentProductCreateRequest request);
    
    void modify(ExperimentProductModifyRequest request);
    
    void delete(Integer id);
    
    boolean checkedName(ExperimentProductPagingRequest connect);
}
