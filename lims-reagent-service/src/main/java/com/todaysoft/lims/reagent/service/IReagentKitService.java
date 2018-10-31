package com.todaysoft.lims.reagent.service;

import java.util.List;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.reagent.entity.ReagentKit;
import com.todaysoft.lims.reagent.model.request.ReagentKitModel;

public interface IReagentKitService
{
    Pagination<ReagentKit> paging(ReagentKitModel request);
    
    List<ReagentKit> list(ReagentKitModel request);
    
    ReagentKit get(String id);
    
    void create(ReagentKitModel request);
    
    void modify(ReagentKit request);
    
    void delete(String id);
    
    boolean validate(ReagentKit request);
    
    Pagination<ReagentKit> selectReagentKit(ReagentKitModel request);

    List<ReagentKit> listByTaskId(String id);
}
