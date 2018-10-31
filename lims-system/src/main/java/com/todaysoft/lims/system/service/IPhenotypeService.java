package com.todaysoft.lims.system.service;

import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.todaysoft.lims.system.model.searcher.PhenotypeSearcher;
import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.Phenotype;
import com.todaysoft.lims.system.model.vo.disease.DiseasePhenotype;

public interface IPhenotypeService
{
    
    void create(Phenotype request);
    
    void modify(Phenotype request);
    
    void delete(PhenotypeSearcher searcher);
    
    Pagination<Phenotype> paging(PhenotypeSearcher searcher, int pageNo, int pageSize);
    
    Phenotype getPhenotypeById(String id);
    
    boolean checkedName(PhenotypeSearcher connect);
    
    List<Phenotype> getPhenotypeList(PhenotypeSearcher searcher);
    
    List<Phenotype> upload(MultipartFile uploadData);
    
    List<DiseasePhenotype> getDiseasePhenotype(String id);
    
    void downloadData(HttpServletResponse response, InputStream inputStream);
    
    Phenotype getPhenotypeByCode(String object);
    
}
