package com.todaysoft.lims.reagent.service;

import java.util.List;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.reagent.entity.Primer;
import com.todaysoft.lims.reagent.model.request.CheckPrimerForDesignRequest;
import com.todaysoft.lims.reagent.model.request.PrimerCreateRequest;
import com.todaysoft.lims.reagent.model.request.PrimerExcelListRequest;
import com.todaysoft.lims.reagent.model.request.PrimerModifyRequest;
import com.todaysoft.lims.reagent.model.request.PrimerPagingRequest;

public interface IPrimerService
{
    Pagination<Primer> paging(PrimerPagingRequest request);
    
    List<Primer> list(PrimerPagingRequest request);
    
    List<Primer> pcrNgsList(PrimerPagingRequest request);
    
    Primer getPrimer(String id);
    
    Primer getByName(String name);
    
    String create(PrimerCreateRequest request);
    
    void modify(PrimerModifyRequest request);
    
    void delete(String id);
    
    boolean checkedPrimerNo(String primerNo);
    
    String checkPrimerForDesign(CheckPrimerForDesignRequest request);
    
    List<Primer> getListByChromAndPcr(CheckPrimerForDesignRequest request);
    
    List<Primer> getListByProductCodeAndType(CheckPrimerForDesignRequest request);
    
    void excelDataInsert(PrimerExcelListRequest request);
    
    List<Primer> selectByProperties(Primer p);
    
}
