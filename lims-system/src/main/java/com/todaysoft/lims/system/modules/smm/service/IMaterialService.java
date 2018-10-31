package com.todaysoft.lims.system.modules.smm.service;

import java.util.List;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.system.modules.smm.model.MaterialModel;
import com.todaysoft.lims.system.modules.smm.model.MaterialSearcher;
import com.todaysoft.lims.system.modules.smm.service.request.MaterialCreateRequest;

public interface IMaterialService
{
    Pagination<MaterialModel> sortPaging(MaterialSearcher searcher);
    
    void create(MaterialCreateRequest request);
    
    boolean validate(MaterialSearcher request);
    
    MaterialModel getById(String id);
    
    void modify(MaterialCreateRequest request);
    
    Integer delete(String id);
    
    void setDisable(String id);
    
    void setEnable(String id);
    
    List<MaterialModel> getSortList();
    
    MaterialModel getByName(String equalName);
}
