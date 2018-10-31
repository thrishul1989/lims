package com.todaysoft.lims.system.service;

import java.util.List;

import com.todaysoft.lims.system.model.searcher.ProbeSeacher;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.Probe;

public interface IProbeService
{
    
    Pagination<Probe> paging(ProbeSeacher searcher, int pageNo, int pageSize);
    
    List<Probe> list(ProbeSeacher searcher);
    
    void modify(ProbeSeacher request);
    
    Probe getProbe(String id);
    
    String getProbeNext();
    
    void deleteProbe(String id);
    
    void createProbe(ProbeSeacher request);
    
    boolean validate(ProbeSeacher request);
    
}
