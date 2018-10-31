package com.todaysoft.lims.testing.base.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.persist.BaseDaoSupport;
import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.testing.base.entity.ProductCancelRecord;
import com.todaysoft.lims.testing.base.model.ProductScheduleCancelSearcher;
import com.todaysoft.lims.testing.base.service.IProductCancelScheduleService;

@Service
public class ProductCancelScheduleServiceImpl implements IProductCancelScheduleService
{
    
    @Autowired
    private BaseDaoSupport baseDaoSupport;
    
    @Override
    public Pagination<ProductCancelRecord> paging(ProductScheduleCancelSearcher searcher)
    {
        Pagination<ProductCancelRecord> paging =
            baseDaoSupport.find(searcher.toQuery(), searcher.getPageNo(), searcher.getPageSize(), ProductCancelRecord.class);
        return paging;
    }
    
    @Override
    public ProductCancelRecord getProductCancelRecordById(String id)
    {
        return baseDaoSupport.get(ProductCancelRecord.class, id);
    }
    
}
