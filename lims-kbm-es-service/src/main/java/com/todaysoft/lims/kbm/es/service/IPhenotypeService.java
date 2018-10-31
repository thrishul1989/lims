package com.todaysoft.lims.kbm.es.service;

import java.util.List;

import com.todaysoft.lims.kbm.es.model.Phenotype;

public interface IPhenotypeService
{
    String ES_TYPE = "PHENOTYPE";
    
    boolean index(Phenotype phenotype);
    
    List<Phenotype> search(String keywords, int offset, int limit);
    
    boolean remove(String id);
}
