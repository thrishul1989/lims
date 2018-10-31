package com.todaysoft.lims.system.service;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.todaysoft.lims.system.model.vo.Pagination;
import com.todaysoft.lims.system.model.vo.disease.Disease;
import com.todaysoft.lims.system.model.vo.disease.DiseaseFormRequest;
import com.todaysoft.lims.system.model.vo.disease.DiseaseGeneFormRequest;
import com.todaysoft.lims.system.model.vo.disease.DiseaseGenePagingRequest;
import com.todaysoft.lims.system.model.vo.disease.DiseasePagingRequest;
import com.todaysoft.lims.system.model.vo.disease.Gene;
import com.todaysoft.lims.system.service.impl.IndexESMonitor;

public interface IDiseaseGeneService
{
    
    String createGene(DiseaseGeneFormRequest request);
    
    Pagination<Gene> paging(DiseaseGenePagingRequest searcher, int pageNo, int defaultpagesize);
    
    void deleteGene(String id);
    
    Pagination<Disease> pagingDisease(DiseasePagingRequest request, int pageNo, int defaultpagesize);
    
    String createDisease(DiseaseFormRequest request);
    
    void deleteDisease(String id);
    
    List<Gene> geneList(DiseaseGenePagingRequest request);
    
    Gene getGeneById(String id);
    
    Gene getGeneByCode(String code);
    
    boolean validateDiseaseName(DiseaseFormRequest request);
    
    boolean validateGeneName(DiseaseGeneFormRequest request);
    
    String updateGene(DiseaseGeneFormRequest request);
    
    Disease getDiseaseById(String id);
    
    Disease getDiseaseByCode(String code);
    
    String updateDisease(DiseaseFormRequest request);
    
    File upload(HttpServletRequest request, HttpServletResponse response);
    
    void analyticalDisease(HttpServletRequest request, HttpServletResponse response);
    
    List<DiseaseGeneFormRequest> analyticalGene(HttpServletRequest request, HttpServletResponse response);
    
    void quickUploadDisease(HttpServletRequest request, HttpServletResponse response);
    
    boolean validateGeneCode(DiseaseGeneFormRequest request);
    
    boolean validateDiseaseCode(DiseaseFormRequest request);
    
    List<Disease> diseaseSelect(DiseasePagingRequest request);
    
    Integer getProductDiease(String diseaseId);
    
    Integer getProductGenes(String geneId);
    
    Disease getDiseaseByENName(DiseaseGeneFormRequest request);
    
    void indexForProducts();
    
    void indexForGenes();
    
    void indexForDiseases();
    
    void indexForPhenotypes();
    
    IndexESMonitor getIndexESMonitor();
}
