package com.todaysoft.lims.gateway.service.adapter;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.todaysoft.lims.gateway.model.Pagination;
import com.todaysoft.lims.gateway.model.ReceivedSample;
import com.todaysoft.lims.gateway.model.request.order.OrderSampleView;
import com.todaysoft.lims.gateway.model.request.order.OrderSearchRequest;
import com.todaysoft.lims.gateway.model.request.samplereceiving.SampleReceiving;
import com.todaysoft.lims.gateway.model.request.samplereceiving.SampleReceivingFormRequest;
import com.todaysoft.lims.gateway.model.request.samplereceiving.SampleStoragingFormRequest;
import com.todaysoft.lims.gateway.model.request.samplereceiving.SampleTransferFormRequest;
import com.todaysoft.lims.gateway.model.request.samplereceiving.SampleTransferring;
import com.todaysoft.lims.gateway.model.request.samplereceiving.SampleTransferringDetail;


@Component
public class SampleReceivingAdapter extends AbstractAdapter {
	
	private static final String SERVICE_NAME = "lims-sampleReceive-service";
	@Override
	public String getName() {
		return SERVICE_NAME;
	}
	@Autowired
	private RestTemplate template;
	
	@HystrixCommand(fallbackMethod = "fallback")
	public String update(SampleReceivingFormRequest request){
		String url = getServiceUrl("/sampleReceiving/update");
		return template.postForObject(url, request, String.class);
	}


	@HystrixCommand(fallbackMethod = "fallback")
	public String create(SampleReceivingFormRequest request) {
		String url = getServiceUrl("/sampleReceiving/create");
		return template.postForObject(url, request, String.class);
	}


	public List<OrderSampleView> getSampleByView(OrderSearchRequest request) {
		String url = getServiceUrl("/sampleReceiving/getSampleByView");
		ResponseEntity<List<OrderSampleView>> exchange =
        template.exchange(url, HttpMethod.POST, new HttpEntity<OrderSearchRequest>(request), new ParameterizedTypeReference<List<OrderSampleView>>()
	        {
	        });
        return exchange.getBody();
	}


	public void createTransfer(SampleTransferFormRequest request) {
		String url = getServiceUrl("/sampleReceiving/createTransfer");
		template.postForObject(url, request, String.class);
	}


	public List<ReceivedSample> getTransferSample(OrderSearchRequest request) {
		String url = getServiceUrl("/sampleReceiving/getTransferSample");
		ResponseEntity<List<ReceivedSample>> exchange =
        template.exchange(url, HttpMethod.POST, new HttpEntity<OrderSearchRequest>(request), new ParameterizedTypeReference<List<ReceivedSample>>()
	        {
	        });
        return exchange.getBody();
	}


	public void createStoraging(SampleStoragingFormRequest request) {
		String url = getServiceUrl("/sampleReceiving/createStoraging");
		template.postForObject(url, request, String.class);
	}

	public Pagination<SampleReceiving> sampleReceivingPaging(SampleReceiving request){
		String url = getServiceUrl("/sampleReceiving/sampleReceivingPaging");
		ResponseEntity<Pagination<SampleReceiving>> exchange = template.exchange(url,
				HttpMethod.POST, new HttpEntity<SampleReceiving>(request),
				new ParameterizedTypeReference<Pagination<SampleReceiving>>() {
				});
		return exchange.getBody();
	}
	
	public Pagination<SampleTransferringDetail> sampleTransferringPaging(SampleTransferringDetail request){
		String url = getServiceUrl("/sampleReceiving/sampleTransferringPaging");
		ResponseEntity<Pagination<SampleTransferringDetail>> exchange = template.exchange(url,
				HttpMethod.POST, new HttpEntity<SampleTransferringDetail>(request),
				new ParameterizedTypeReference<Pagination<SampleTransferringDetail>>() {
				});
		return exchange.getBody();
	}
	
	
}
