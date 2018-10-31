package com.todaysoft.lims.system.model.vo;

import java.util.List;



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
