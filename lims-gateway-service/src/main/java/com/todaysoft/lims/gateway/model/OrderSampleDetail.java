package com.todaysoft.lims.gateway.model;

import java.util.List;

import com.todaysoft.lims.gateway.model.request.samplereceive.SampleReceiveOrder;

public class OrderSampleDetail {
	
	private SampleReceiveOrder order;
	private List<OrderSampleDetailTrack> sampleDetailTrackList; //样本明细中单个样本的跟踪记录

	public SampleReceiveOrder getOrder() {
		return order;
	}

	public void setOrder(SampleReceiveOrder order) {
		this.order = order;
	}

	public List<OrderSampleDetailTrack> getSampleDetailTrackList() {
		return sampleDetailTrackList;
	}

	public void setSampleDetailTrackList(
			List<OrderSampleDetailTrack> sampleDetailTrackList) {
		this.sampleDetailTrackList = sampleDetailTrackList;
	}
}
