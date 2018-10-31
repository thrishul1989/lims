package com.todaysoft.lims.gateway.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.ReceivedSample;
import com.todaysoft.lims.gateway.model.request.order.OrderSampleView;
import com.todaysoft.lims.gateway.model.request.order.OrderSearchRequest;
import com.todaysoft.lims.gateway.model.request.samplereceiving.SampleReceiving;
import com.todaysoft.lims.gateway.model.request.samplereceiving.SampleReceivingFormRequest;
import com.todaysoft.lims.gateway.model.request.samplereceiving.SampleStoragingFormRequest;
import com.todaysoft.lims.gateway.model.request.samplereceiving.SampleTransferFormRequest;
import com.todaysoft.lims.gateway.model.request.samplereceiving.SampleTransferringDetail;
import com.todaysoft.lims.gateway.service.ISampleReceivingService;

@RestController
@RequestMapping("/sampleReceiving")
public class SampleReceivingController {

	@Autowired
	private ISampleReceivingService service;
	
	@RequestMapping(value = "/sampleReceivingPaging")
    public Pagination<SampleReceiving> sampleReceivingPaging(@RequestBody SampleReceiving request){
	
     return   service.sampleReceivingPaging(request);
    }
	
	
	@RequestMapping(value = "/sampleTransferringPaging")
    public Pagination<SampleTransferringDetail> sampleTransferringPaging(@RequestBody SampleTransferringDetail request){
	
     return   service.sampleTransferringPaging(request);
    }
	
	
	
	
    @RequestMapping(value = "/create")
    public void create(@RequestBody SampleReceivingFormRequest request){
        service.create(request);
    }
    
    @RequestMapping(value = "/update")
    public void update(@RequestBody SampleReceivingFormRequest request){
        service.update(request);
    }
    
    
    
    @RequestMapping(value = "/getSampleByView")
    public List<OrderSampleView> getSampleByView(@RequestBody OrderSearchRequest request){
		return service.getSampleByView(request);
	}
    
    
    @RequestMapping(value = "/createTransfer")
    public void createTransfer(@RequestBody SampleTransferFormRequest request){
        service.createTransfer(request);
    }
    
    
    @RequestMapping(value = "/getTransferSample")
    public List<ReceivedSample> getTransferSample(@RequestBody OrderSearchRequest request){
		return service.getTransferSample(request);
	}
    
    @RequestMapping(value = "/createStoraging")
    public void createStoraging(@RequestBody SampleStoragingFormRequest request){
        service.createStoraging(request);
    }
	
}
