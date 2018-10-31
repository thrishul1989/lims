package com.todaysoft.lims.gateway.service;

import java.util.List;

import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.Phenotype;
import com.todaysoft.lims.gateway.model.request.PhenotypeRequest;


public interface IPhenotypeService {

	void create(Phenotype request);

	void modify(Phenotype request);

	void delete(String id);

	Pagination<Phenotype> paging(PhenotypeRequest searcher);

	Phenotype getPhenotypeById(String id);
	
	boolean checkedName(PhenotypeRequest connect);
	
	List<Phenotype> getPhenotypeList(PhenotypeRequest searcher);
}
