package com.todaysoft.lims.reagent.service;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.reagent.entity.ProducterManage;
import com.todaysoft.lims.reagent.model.request.ProducterManageRequest;

public interface IProducterManageService
{
    Pagination<ProducterManage> paging(ProducterManageRequest request);
    
    ProducterManage get(String id);
    
    String create(ProducterManage request);
    
    void modify(ProducterManage request);
    
    void delete(String id);
    
    boolean deleteEmail(ProducterManage data);;
    
    boolean validate(ProducterManage request);
    
}
