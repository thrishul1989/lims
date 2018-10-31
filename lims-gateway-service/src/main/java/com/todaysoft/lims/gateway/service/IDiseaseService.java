package com.todaysoft.lims.gateway.service;

import java.util.List;

import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.request.disease.Disease;
import com.todaysoft.lims.gateway.model.request.disease.DiseaseFormRequest;
import com.todaysoft.lims.gateway.model.request.disease.DiseasePagingRequest;
import com.todaysoft.lims.gateway.model.request.disease.Gene;
import com.todaysoft.lims.gateway.model.request.disease.DiseaseGeneFormRequest;
import com.todaysoft.lims.gateway.model.request.disease.DiseaseGenePagingRequest;


public interface IDiseaseService {

	String create(DiseaseGeneFormRequest request);

	Pagination<Gene> paging(DiseaseGenePagingRequest request);

	void delete(String id);

	Pagination<Disease> pagingDisease(DiseasePagingRequest request);

	String createDisease(DiseaseFormRequest request);

	void deleteDisease(String id);

	List<Gene> geneList(DiseaseGenePagingRequest searcher);

	Gene getGeneById(String id);
	Gene getGeneByCode(String code);
	boolean validateDiseaseName(DiseaseFormRequest request);

	boolean validateGeneName(DiseaseGeneFormRequest request);

	String updateGene(DiseaseGeneFormRequest request);

	Disease getDiseaseById(String id);
	Disease getDiseaseByCode(String code);

	String updateDisease(DiseaseFormRequest request);

	boolean validateDiseaseCode(DiseaseGeneFormRequest request);

	boolean validateGeneCode(DiseaseGeneFormRequest request);

	List<Disease> diseaseSelect(DiseasePagingRequest request);



}
