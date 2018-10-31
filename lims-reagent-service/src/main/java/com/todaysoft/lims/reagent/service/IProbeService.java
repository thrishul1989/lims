package com.todaysoft.lims.reagent.service;

import java.util.List;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.reagent.entity.Probe;
import com.todaysoft.lims.reagent.model.request.ProbeCreateRequest;
import com.todaysoft.lims.reagent.model.request.ProbeListRequest;
import com.todaysoft.lims.reagent.model.request.ProbeModifyRequest;
import com.todaysoft.lims.reagent.model.request.ProbePagingRequest;

public interface IProbeService
{
    Pagination<Probe> paging(ProbePagingRequest request);
    
    List<Probe> list(ProbeListRequest request);
    
    Probe get(String id);
    
    Probe getByName(String name);
    
    String create(ProbeCreateRequest request);
    
    void modify(ProbeModifyRequest request);
    
    void delete(String id);
    
    boolean validate(Probe request);
    
    List<Probe> getContactProducts(List<Integer> productIds);
    
    String getProbeNext();
    
}
