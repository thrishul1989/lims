package com.todaysoft.lims.kbm.es.service;

import java.util.List;

import com.todaysoft.lims.kbm.es.model.Disease;

public interface IDiseaseService
{
    String ES_TYPE = "DISEASE";
    
    boolean index(Disease disease);
    
    List<Disease> search(String keywords, int offset, int limit);
    
    boolean remove(String id);
}
