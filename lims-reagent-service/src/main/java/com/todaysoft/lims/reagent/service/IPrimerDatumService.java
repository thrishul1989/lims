package com.todaysoft.lims.reagent.service;

import java.util.List;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.reagent.entity.PrimerDatum;
import com.todaysoft.lims.reagent.model.request.PrimerDatumListRequest;
import com.todaysoft.lims.reagent.model.request.PrimerDatumRequest;

public interface IPrimerDatumService
{
    Pagination<PrimerDatum> paging(PrimerDatumListRequest request);
    
    List<PrimerDatum> list(PrimerDatumListRequest request);
    
    PrimerDatum get(String id);
    
    String create(PrimerDatum request);
    
    void modify(PrimerDatum request);
    
    void delete(String id);
    
    void uploadData(PrimerDatumRequest request);
}
