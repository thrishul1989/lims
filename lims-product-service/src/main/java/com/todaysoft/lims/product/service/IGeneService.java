package com.todaysoft.lims.product.service;

import java.util.List;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.product.entity.disease.Gene;
import com.todaysoft.lims.product.model.request.GenePagingRequest;

public interface IGeneService
{
    
    Pagination<Gene> paging(GenePagingRequest request);
    
    List<Gene> list(GenePagingRequest request);
    
}
