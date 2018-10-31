package com.todaysoft.lims.reagent.service;

import java.util.List;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.reagent.entity.Reagent;
import com.todaysoft.lims.reagent.model.request.ReagentCodeUniqueRequest;
import com.todaysoft.lims.reagent.model.request.ReagentCreateRequest;
import com.todaysoft.lims.reagent.model.request.ReagentListRequest;
import com.todaysoft.lims.reagent.model.request.ReagentModifyRequest;

public interface IReagentService
{
    Pagination<Reagent> paging(ReagentListRequest request);
    
    List<Reagent> list(ReagentListRequest request);
    
    Reagent get(String id);
    
    boolean unique(ReagentCodeUniqueRequest request);
    
    String create(ReagentCreateRequest request);
    
    void modify(ReagentModifyRequest request);
    
    void delete(String id);
}
