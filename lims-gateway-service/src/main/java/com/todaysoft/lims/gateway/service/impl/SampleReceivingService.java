package com.todaysoft.lims.gateway.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.todaysoft.lims.gateway.service.ISampleReceivingService;
import com.todaysoft.lims.gateway.service.adapter.SampleReceivingAdapter;


@Service
public class SampleReceivingService implements ISampleReceivingService {

	@Autowired
	private SampleReceivingAdapter sampleReceivingAdapter;

	
	@Override
	public void create(SampleReceivingFormRequest request) {
		sampleReceivingAdapter.create(request);
	}

	@Override
	public void update(SampleReceivingFormRequest request) {
		sampleReceivingAdapter.update(request);
	}

	@Override
	public List<OrderSampleView> getSampleByView(OrderSearchRequest request) {
		return sampleReceivingAdapter.getSampleByView(request);
	}

	@Override
	public void createTransfer(SampleTransferFormRequest request) {
		sampleReceivingAdapter.createTransfer(request);
	}

	@Override
	public List<ReceivedSample> getTransferSample(OrderSearchRequest request) {
		return sampleReceivingAdapter.getTransferSample(request);
	}

	@Override
	public void createStoraging(SampleStoragingFormRequest request) {
		sampleReceivingAdapter.createStoraging(request);
		
	}

	@Override
	public Pagination<SampleReceiving> sampleReceivingPaging(
			SampleReceiving request) {
		// TODO Auto-generated method stub
		return sampleReceivingAdapter.sampleReceivingPaging(request);
	}

	@Override
	public Pagination<SampleTransferringDetail> sampleTransferringPaging(
			SampleTransferringDetail request) {
		return sampleReceivingAdapter.sampleTransferringPaging(request);
	}

}
