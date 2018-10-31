package com.todaysoft.lims.gateway.model.request;

public class OrderSampleListRequest {
	
	private Integer orderId;
	private Integer sampleDetailId;

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getSampleDetailId() {
		return sampleDetailId;
	}

	public void setSampleDetailId(Integer sampleDetailId) {
		this.sampleDetailId = sampleDetailId;
	}
}
