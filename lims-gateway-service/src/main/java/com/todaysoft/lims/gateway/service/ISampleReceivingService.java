package com.todaysoft.lims.gateway.service;

import java.util.List;

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


public interface ISampleReceivingService {

	void create(SampleReceivingFormRequest request);

	void update(SampleReceivingFormRequest request);

	List<OrderSampleView> getSampleByView(OrderSearchRequest request);

	void createTransfer(SampleTransferFormRequest request);

	List<ReceivedSample> getTransferSample(OrderSearchRequest request);

	void createStoraging(SampleStoragingFormRequest request);

	
	Pagination<SampleReceiving> sampleReceivingPaging(SampleReceiving request);
	Pagination<SampleTransferringDetail> sampleTransferringPaging(SampleTransferringDetail request);
	

}
