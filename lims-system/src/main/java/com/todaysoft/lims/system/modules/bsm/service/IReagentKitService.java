package com.todaysoft.lims.system.modules.bsm.service;

import java.util.List;

import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.ReagentKit;

public interface IReagentKitService
{
    Pagination<ReagentKit> paging(ReagentKit searcher, int pageNo, int pageSize);
    
    void modify(ReagentKit request);
    
    ReagentKit getReagentKit(String id);
    
    void deleteReagentKit(String id);
    
    void createReagentKit(ReagentKit request);
    
    boolean validate(ReagentKit request);
    
    Pagination<ReagentKit> selectReagentKit(ReagentKit req, int pageNo, int pageSize);
    
    List<ReagentKit> list(ReagentKit request);

    List<ReagentKit> listByTaskId(ReagentKit reagentKit);
}
