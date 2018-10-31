package com.todaysoft.lims.gateway.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.request.disease.Disease;
import com.todaysoft.lims.gateway.model.request.disease.DiseaseFormRequest;
import com.todaysoft.lims.gateway.model.request.disease.DiseasePagingRequest;
import com.todaysoft.lims.gateway.model.request.disease.Gene;
import com.todaysoft.lims.gateway.model.request.disease.DiseaseGeneFormRequest;
import com.todaysoft.lims.gateway.model.request.disease.DiseaseGenePagingRequest;
import com.todaysoft.lims.gateway.service.IDiseaseService;
import com.todaysoft.lims.gateway.service.adapter.DiseaseAdapter;
@Service
public class DiseaseService implements IDiseaseService {

	@Autowired
	private DiseaseAdapter adapter;
	
	@Override
	public String create(DiseaseGeneFormRequest request) {
		return adapter.create(request);
	}

	@Override
	public Pagination<Gene> paging(DiseaseGenePagingRequest request) {
		return adapter.paging(request);
	}

	@Override
	public void delete(String id) {
		adapter.deleteGene(id);
		
	}

	@Override
	public Pagination<Disease> pagingDisease(DiseasePagingRequest request) {
		return adapter.pagingDisease(request);
	}

	@Override
	public String createDisease(DiseaseFormRequest request) {
		return adapter.createDisease( request);
	}

	@Override
	public void deleteDisease(String id) {
		adapter.deleteDisease(id);
	}

	@Override
	public List<Gene> geneList(DiseaseGenePagingRequest searcher) {
		return adapter.geneList(searcher);
	}

	@Override
	public Gene getGeneById(String id) {
		return adapter.getGeneById(id);
		
	}

	@Override
	public boolean validateDiseaseName(DiseaseFormRequest request) {
		return adapter.validateDiseaseName(request);
	}

	@Override
	public boolean validateGeneName(DiseaseGeneFormRequest request) {
		return adapter.validateGeneName(request);
	}

	@Override
	public String updateGene(DiseaseGeneFormRequest request) {
		return adapter.updateGene(request);
	}

	@Override
	public Disease getDiseaseById(String id) {
		return adapter.getDiseaseById(id);
	}

	@Override
	public String updateDisease(DiseaseFormRequest request) {
		return adapter.updateDisease(request);
	}

	@Override
	public boolean validateDiseaseCode(DiseaseGeneFormRequest request) {
		return adapter.validateDiseaseCode(request);
	}

	@Override
	public boolean validateGeneCode(DiseaseGeneFormRequest request) {
		return adapter.validateGeneCode(request);
	}


	@Override
	public Disease getDiseaseByCode(String code) {
		return adapter.getDiseaseByCode(code);
	}

	@Override
	public Gene getGeneByCode(String code) {
		return adapter.getGeneByCode(code);
	}


	@Override
	public List<Disease> diseaseSelect(DiseasePagingRequest request) {
		return adapter.diseaseSelect(request);
	}

}
