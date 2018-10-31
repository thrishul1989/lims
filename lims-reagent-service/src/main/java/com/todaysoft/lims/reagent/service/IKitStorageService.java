package com.todaysoft.lims.reagent.service;

import java.util.List;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.reagent.dao.searcher.KitStorageSearcher;
import com.todaysoft.lims.reagent.entity.KitStorage;

public interface IKitStorageService
{
    Pagination<KitStorage> paging(KitStorageSearcher request);
    
    Integer create(KitStorageSearcher request);
    
    void modifyReaction(KitStorageSearcher request);
    
    KitStorage get(Integer id);
    
    void delete(Integer id);
    
    List<KitStorage> list(KitStorageSearcher request);
    
    Integer countByKitId(Integer kitId);
}
