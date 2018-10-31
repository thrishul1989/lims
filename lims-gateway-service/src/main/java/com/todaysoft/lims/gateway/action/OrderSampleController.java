package com.todaysoft.lims.gateway.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.gateway.model.OrderSample;
import com.todaysoft.lims.gateway.model.OrderSampleDetail;
import com.todaysoft.lims.gateway.model.request.OrderSampleCreateRequest;
import com.todaysoft.lims.gateway.model.request.OrderSampleListRequest;
import com.todaysoft.lims.gateway.service.IOrderSampleService;

@RestController
@RequestMapping("/orderSample")
public class OrderSampleController {
	
	@Autowired
	private IOrderSampleService service;
	
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public List<OrderSample> list(@RequestBody OrderSampleListRequest request){
		return service.list(request);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public OrderSample get(@PathVariable Integer id){
		return service.get(id);
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public Integer create(@RequestBody OrderSampleCreateRequest request){
		return service.create(request);
	}
	
	@RequestMapping(value = "/orderSampleDetailTrack/{orderId}", method = RequestMethod.GET)
	public OrderSampleDetail orderSampleDetailList(@PathVariable Integer orderId){
		return service.getOrderSampleDetail(orderId);
	}
}
