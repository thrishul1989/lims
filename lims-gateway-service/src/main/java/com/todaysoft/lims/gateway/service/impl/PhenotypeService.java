package com.todaysoft.lims.gateway.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.Phenotype;
import com.todaysoft.lims.gateway.model.request.PhenotypeRequest;
import com.todaysoft.lims.gateway.service.IPhenotypeService;
import com.todaysoft.lims.gateway.service.adapter.PhenotypeAdapter;

@Service
public class PhenotypeService implements IPhenotypeService {

	@Autowired
	private PhenotypeAdapter adapter;
	@Override
	public void create(Phenotype request) {
		
		adapter.create(request);
	}

	@Override
	public void modify(Phenotype request) {
		
		adapter.modify(request);
	}

	@Override
	public void delete(String id) {
		
		adapter.delete(id);
	}

	@Override
	public Pagination<Phenotype> paging(PhenotypeRequest searcher) {
		
		return adapter.paging(searcher);
	}

	@Override
	public Phenotype getPhenotypeById(String id) {
		
		return adapter.getPhenotypeById(id);
	}

	@Override
	public boolean checkedName(PhenotypeRequest phenotype) {
		
		return adapter.checkedName(phenotype);
	}

	@Override
	public List<Phenotype> getPhenotypeList(PhenotypeRequest request) {
		
		return adapter.getPhenotypeList(request);
	}

}
