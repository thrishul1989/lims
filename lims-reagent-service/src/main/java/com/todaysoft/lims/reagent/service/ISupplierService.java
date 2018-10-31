package com.todaysoft.lims.reagent.service;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.reagent.dao.searcher.SupplierSearcher;
import com.todaysoft.lims.reagent.entity.Supplier;

public interface ISupplierService
{
    Pagination<Supplier> paging(SupplierSearcher request);
    
    //	List<Supplier> list(Supplier request);
    
    Supplier get(Integer id);
    
    Integer create(Supplier request);
    
    void modify(Supplier request);
    
    void delete(Integer id);
    
    boolean validate(Supplier request);
    
    Pagination<Supplier> selectSupplier(SupplierSearcher request);
}
