package com.todaysoft.lims.kbm.es.service;

import java.util.List;

import com.todaysoft.lims.kbm.es.model.Gene;

public interface IGeneService
{
    String ES_TYPE = "GENE";
    
    boolean index(Gene gene);
    
    List<Gene> search(String keywords, int offset, int limit);
    
    boolean remove(String id);
}
