package com.todaysoft.lims.product.service;

import java.util.List;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.product.entity.Phenotype;
import com.todaysoft.lims.product.model.request.PhenotypeRequest;
import com.todaysoft.lims.product.model.response.PhenotypePageModel;

public interface IPhenotypeService
{
    
    void create(Phenotype request);
    
    void modify(Phenotype request);
    
    void delete(PhenotypeRequest searcher);
    
    Pagination<PhenotypePageModel> paging(PhenotypeRequest searcher);
    
    PhenotypePageModel getPhenotypeById(String id);
    
    boolean checkedName(PhenotypeRequest connect);
    
    List<PhenotypePageModel> getPhenotypeList(PhenotypeRequest searcher);
    
    PhenotypePageModel getPhenotypeByCode(String code);
    
    void sendPhenotypeProduce(String id, String string);
    
}
