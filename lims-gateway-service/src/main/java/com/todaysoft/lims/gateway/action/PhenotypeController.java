package com.todaysoft.lims.gateway.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.Phenotype;
import com.todaysoft.lims.gateway.model.request.PhenotypeRequest;
import com.todaysoft.lims.gateway.service.IPhenotypeService;


@RestController
@RequestMapping("/phenotype")
public class PhenotypeController {

	@Autowired
	private IPhenotypeService service;
	
	@RequestMapping(value="/list.do")
	public Pagination<Phenotype> paging(@RequestBody PhenotypeRequest searcher){
		
		return service.paging(searcher);
	}
	
	@RequestMapping(value="/create.do")
	public void create(@RequestBody Phenotype request){
		
		service.create(request);
	}

	@RequestMapping(value="/modify.do")
	public void modify(@RequestBody Phenotype request){
		
		service.modify(request);
	}
	
	@RequestMapping(value="/delete/{id}")
	public void delete(@PathVariable String id){
		
		service.delete(id);
	}

	@RequestMapping(value="/getPhenotype/{id}")
	public Phenotype getPhenotypeById(@PathVariable String id){
		
		return service.getPhenotypeById(id);
	}
	
	@RequestMapping(value="/validate.do")
	public boolean checkedName(@RequestBody PhenotypeRequest searcher){
		
		return service.checkedName(searcher);
	}
	
	@RequestMapping(value="/getPhenotypes")
	public List<Phenotype> getPhenotypes(@RequestBody PhenotypeRequest searcher){
		
		return service.getPhenotypeList(searcher);
	}
}
