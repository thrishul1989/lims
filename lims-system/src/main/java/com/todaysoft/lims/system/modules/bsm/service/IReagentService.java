package com.todaysoft.lims.system.modules.bsm.service;

import java.util.List;

import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.Reagent;
import com.todaysoft.lims.system.modules.bsm.model.ReagentSeacher;
import com.todaysoft.lims.system.modules.bsm.service.request.ReagentCreateRequest;
import com.todaysoft.lims.system.modules.bsm.service.request.ReagentModifyRequest;

public interface IReagentService
{
    Pagination<Reagent> paging(ReagentSeacher seacher, int pageNo, int pageSize);
    
    List<Reagent> list(ReagentSeacher searcher);
    
    Reagent get(String id);
    
    boolean unique(String id, String code);
    
    String create(ReagentCreateRequest request);
    
    void modify(ReagentModifyRequest request);
    
    void delete(String id);
}
