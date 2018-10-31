package com.todaysoft.lims.reagent.service;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.reagent.dao.searcher.SupplierReagentSearcher;
import com.todaysoft.lims.reagent.entity.SupplierReagent;
import com.todaysoft.lims.reagent.entity.SupplierReagentKit;

public interface ISupplierReagentService
{
    
    Pagination<SupplierReagent> paging(SupplierReagentSearcher request);
    
    Integer create(SupplierReagentSearcher request);
    
    void delete(Integer id);
    
    SupplierReagent get(Integer id);
    
    void updateReagentPrice(SupplierReagent supplierReagent);
    
    boolean validate(SupplierReagent supplierReagent);
    
    boolean validate(SupplierReagentKit supplierReagent);
    
}
