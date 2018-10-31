package com.todaysoft.lims.gateway.model;

import java.util.List;

import com.todaysoft.lims.gateway.model.request.samplereceive.SampleReceiveDetail;

public class OrderSampleDetailTrack {
	
	private SampleReceiveDetail sampleDetail;
	private List<OrderSample> orderSampleList; 

	public SampleReceiveDetail getSampleDetail() {
		return sampleDetail;
	}

	public void setSampleDetail(SampleReceiveDetail sampleDetail) {
		this.sampleDetail = sampleDetail;
	}

	public List<OrderSample> getOrderSampleList() {
		return orderSampleList;
	}

	public void setOrderSampleList(List<OrderSample> orderSampleList) {
		this.orderSampleList = orderSampleList;
	}
}
