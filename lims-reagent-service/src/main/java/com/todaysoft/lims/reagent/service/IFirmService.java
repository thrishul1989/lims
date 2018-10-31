package com.todaysoft.lims.reagent.service;

import java.util.List;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.reagent.dao.searcher.FirmSearcher;
import com.todaysoft.lims.reagent.entity.Firm;

public interface IFirmService
{
    
    Pagination<Firm> paging(FirmSearcher request);
    
    List<Firm> list(FirmSearcher request);
    
    Firm get(Integer id);
    
    Integer create(FirmSearcher request);
    
    void modify(FirmSearcher request);
    
    void delete(Integer id);
    
    boolean validate(FirmSearcher request);
    
    void enableFirm(FirmSearcher request);
    
    Pagination<Firm> selectFirm(FirmSearcher request);
    
}
