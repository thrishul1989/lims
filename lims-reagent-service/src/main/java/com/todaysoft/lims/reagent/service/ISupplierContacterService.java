package com.todaysoft.lims.reagent.service;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.reagent.dao.searcher.SupplierContacterSearcher;
import com.todaysoft.lims.reagent.entity.SupplierContacter;

public interface ISupplierContacterService
{
    
    Pagination<SupplierContacter> paging(SupplierContacterSearcher request);
    
    Integer create(SupplierContacterSearcher request);
    
    void modify(SupplierContacterSearcher request);
    
    SupplierContacter get(Integer id);
    
    void delete(Integer id);
    
}
