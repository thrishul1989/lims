package com.todaysoft.lims.sample.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.sample.entity.OrderExperimentProduct;
import com.todaysoft.lims.sample.model.request.OrderExperimentProductCreateRequest;
import com.todaysoft.lims.sample.model.request.OrderExperimentProductListRequest;
import com.todaysoft.lims.sample.service.IOrderExperimentProductService;

@RestController
@RequestMapping("/orderExperimentProduct")
public class OrderExperimentProductController {

	@Autowired
	private IOrderExperimentProductService service;
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public List<OrderExperimentProduct> list(@RequestBody OrderExperimentProductListRequest request){
		return service.list(request);
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public Integer create(@RequestBody OrderExperimentProductCreateRequest request){
		return service.create(request);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public OrderExperimentProduct get(@PathVariable Integer id){
		return service.get(id);
	}
	
}
