package com.todaysoft.lims.product.service;

import java.util.List;

import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.product.model.request.DiseaseFormRequest;
import com.todaysoft.lims.product.model.request.DiseaseGeneFormRequest;
import com.todaysoft.lims.product.model.request.DiseaseGenePagingRequest;
import com.todaysoft.lims.product.model.request.DiseasePagingRequest;
import com.todaysoft.lims.product.model.response.DiseasePageModel;
import com.todaysoft.lims.product.model.response.GenePageModel;

public interface IDiseaseService
{
    
    String create(DiseaseGeneFormRequest request);
    
    Pagination<GenePageModel> paging(DiseaseGenePagingRequest request);
    
    String delete(String id);
    
    String createDisease(DiseaseFormRequest request);
    
    String deleteDisease(String id);
    
    Pagination<DiseasePageModel> pagingDisease(DiseasePagingRequest request);
    
    List<GenePageModel> geneList(DiseaseGenePagingRequest searcher);
    
    GenePageModel getGeneById(String id);
    
    GenePageModel getGeneByCode(String code);
    
    boolean validateDiseaseName(DiseaseFormRequest request);
    
    boolean validateGeneName(DiseaseGeneFormRequest request);
    
    String updateGene(DiseaseGeneFormRequest request);
    
    DiseasePageModel getDiseaseById(String id);
    
    DiseasePageModel getDiseaseByCode(String code);
    
    String updateDisease(DiseaseFormRequest request);
    
    boolean validateDiseaseCode(DiseaseFormRequest request);
    
    boolean validateGeneCode(DiseaseFormRequest request);
    
    List<DiseasePageModel> diseaseSelect(DiseasePagingRequest request);
    
    Integer getProductDiease(String diseaseId);
    
    Integer getProductGenes(String geneId);
    
    DiseasePageModel getDiseaseByENName(DiseaseGeneFormRequest request);
    
    List<String> getProductIdDiease(String diseaseId);
    
    List<String> getEnableAndNotModelProductIdDiease(String diseaseId);
    
    boolean createRelationDisease(String id);
    
    boolean createRelationProduct(String id);
    
    boolean createRelationGene(String id);
    
    boolean createRelationPhenotype(String id);
    
    void sendDiseaseProduce(String diseaseId, String string);
    
    void sendGeneProduce(String geneId, String string);
}
