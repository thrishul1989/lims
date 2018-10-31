package com.todaysoft.lims.gateway.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.gateway.model.ExperimentProduct;
import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.request.ExperimentProductCreateRequest;
import com.todaysoft.lims.gateway.model.request.ExperimentProductListRequest;
import com.todaysoft.lims.gateway.model.request.ExperimentProductModifyRequest;
import com.todaysoft.lims.gateway.model.request.ExperimentProductPagingRequest;
import com.todaysoft.lims.gateway.model.request.PhenotypeRequest;
import com.todaysoft.lims.gateway.service.IExperimentProductService;

@RestController
@RequestMapping("/experimentProduct")
public class ExperimentProductController {
	@Autowired
	private IExperimentProductService experimentProductService;
	
	@RequestMapping(value = "/paging",method = RequestMethod.POST)
	public Pagination<ExperimentProduct> paging(@RequestBody ExperimentProductPagingRequest request){
		return experimentProductService.paging(request);
	}
	
	@RequestMapping(value = "/list",method = RequestMethod.POST)
	public List<ExperimentProduct> list(@RequestBody ExperimentProductListRequest request){
		return experimentProductService.list(request);
	}
	
	@RequestMapping(value = "/{id}",method = RequestMethod.GET)
	public ExperimentProduct getExperimentProduct(@PathVariable Integer id){
		return experimentProductService.getExperimentProduct(id);
	}
	
	@RequestMapping(value = "/create",method = RequestMethod.POST)
	public Integer create(@RequestBody ExperimentProductCreateRequest request){
		return experimentProductService.create(request);
	}
	
	@RequestMapping(value = "/modify",method = RequestMethod.POST)
	public void modify(@RequestBody ExperimentProductModifyRequest request){
		experimentProductService.modify(request);
	}
	
	@RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
	public void delete(@PathVariable Integer id){
		experimentProductService.delete(id);
	}
	
	@RequestMapping(value="/validateName.do")
	public boolean checkedName(@RequestBody ExperimentProductPagingRequest searcher){
		
		return experimentProductService.checkedName(searcher);
	}
}
