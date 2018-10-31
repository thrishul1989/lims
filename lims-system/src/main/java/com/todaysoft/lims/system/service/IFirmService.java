package com.todaysoft.lims.system.service;

import com.todaysoft.lims.system.model.vo.Firm;
import com.todaysoft.lims.system.model.vo.Pagination;

public interface IFirmService
{
    Pagination<Firm> paging(Firm searcher, int pageNo, int pageSize);
    
    void modify(Firm request);
    
    Firm get(Integer id);
    
    void delete(Integer id);
    
    void create(Firm request);
    
    boolean validate(Firm request);
    
    void enableFirm(Firm request);
    
    Pagination<Firm> selectFirm(Firm req, int pageNo, int pageSize);
}
