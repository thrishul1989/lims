package com.todaysoft.lims.system.modules.bsm.service;

import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.PrimerDatum;
import com.todaysoft.lims.system.modules.bsm.model.PrimerDatumSearcher;

public interface IPrimerDatumService
{
    Pagination<PrimerDatum> paging(PrimerDatumSearcher searcher, int pageNo, int pageSize);
    
    PrimerDatum get(String id);
    
    String create(PrimerDatum request);
    
    void modify(PrimerDatum request);
    
    void delete(String id);
    
    void upload(MultipartFile contractFile);
    
    String download(InputStream is);
}
