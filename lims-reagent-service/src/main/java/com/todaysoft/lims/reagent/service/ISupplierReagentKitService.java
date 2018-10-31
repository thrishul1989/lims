package com.todaysoft.lims.reagent.service;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.reagent.dao.searcher.SupplierReagentKitSearcher;
import com.todaysoft.lims.reagent.entity.SupplierReagentKit;

public interface ISupplierReagentKitService
{
    
    Pagination<SupplierReagentKit> paging(SupplierReagentKitSearcher request);
    
    Integer createKit(SupplierReagentKitSearcher request);
    
    void delete(Integer id);
    
    SupplierReagentKit get(Integer id);
    
    void updateKitPrice(SupplierReagentKit supplierReagentKit);
    
}
