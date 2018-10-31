package com.todaysoft.lims.system.modules.bcm.service;

import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.disease.Gene;
import com.todaysoft.lims.system.modules.bcm.service.request.GenePagingRequest;

public interface IGeneService
{
    Pagination<Gene> paging(GenePagingRequest searcher, int pageNo, int pageSize);
}
