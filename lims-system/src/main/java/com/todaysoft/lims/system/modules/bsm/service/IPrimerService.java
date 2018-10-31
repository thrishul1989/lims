package com.todaysoft.lims.system.modules.bsm.service;

import com.todaysoft.lims.system.model.form.PrimerFormRequest;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.Primer;
import com.todaysoft.lims.system.modules.bpm.primerdesign.model.CheckPrimerForDesignRequest;
import com.todaysoft.lims.system.modules.bsm.model.PrimerExcelListRequest;
import com.todaysoft.lims.system.modules.bsm.service.request.PrimerPagingRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public interface IPrimerService
{
    
    String create(PrimerFormRequest request);
    
    String excelDataInsert(PrimerExcelListRequest request);
    
    void modify(PrimerFormRequest request);
    
    void delete(String id);
    
    Pagination<Primer> paging(PrimerPagingRequest searcher, int pageNo, int i);
    
    Primer getPrimersById(String id);
    
    boolean checkedPrimerNo(String primerNo);
    
    List<List<String>> analysisFile(MultipartFile file);
    
    List<Primer> getPrimerList(PrimerPagingRequest request);
    
    String checkPrimerForDesign(CheckPrimerForDesignRequest request);
    
    String download(InputStream is,List<Primer> primers);
    
    List<Primer> getByName(PrimerPagingRequest searcher, int pageNo, int i);
    
    List<Primer> selectByProperties(Primer p);
}
