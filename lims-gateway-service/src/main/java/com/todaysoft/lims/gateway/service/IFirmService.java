package com.todaysoft.lims.gateway.service;

import java.util.List;

import com.todaysoft.lims.gateway.model.Firm;
import com.todaysoft.lims.gateway.model.Pagination;

public interface IFirmService
{
    Pagination<Firm> paging(Firm request);
    
    List<Firm> list(Firm request);
    
    Firm get(Integer id);
    
    Integer create(Firm request);
    
    void modify(Firm request);
    
    void delete(Integer id);
    
    boolean validate(Firm request);
    
    void enableFirm(Firm request);
    
    Pagination<Firm> selectFirm(Firm request);
}
