package com.todaysoft.lims.testing.base.service;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.testing.base.entity.ProductCancelRecord;
import com.todaysoft.lims.testing.base.model.ProductScheduleCancelSearcher;

public interface IProductCancelScheduleService
{
    
    Pagination<ProductCancelRecord> paging(ProductScheduleCancelSearcher searcher);
    
    ProductCancelRecord getProductCancelRecordById(String id);
    
}
