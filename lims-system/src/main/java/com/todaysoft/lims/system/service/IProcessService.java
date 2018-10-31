package com.todaysoft.lims.system.service;

import com.todaysoft.lims.system.model.searcher.ProcessSearcher;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.ProcessTaskModel;
import com.todaysoft.lims.system.service.adapter.request.ProcessTaskPagingRequest;




public interface IProcessService
{
    Pagination<ProcessTaskModel> paging(ProcessTaskPagingRequest request, int pageNo, int pageSize);
    
    Pagination<ProcessTaskModel> paging(ProcessSearcher searcher, int pageNo, int pageSize);
    
  
}
