package com.todaysoft.lims.gateway.model.request.samplereceive;

public class OrderListRequest {
	private String orderCode; //订单编号
	
	private String testProduct;//检测产品
	
	private String chargeState; //登记状态
	private int pageNo;
	private int pageSize;
	private Integer orderId;
	

	
	
	public String getChargeState() {
		return chargeState;
	}

	public void setChargeState(String chargeState) {
		this.chargeState = chargeState;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getTestProduct() {
		return testProduct;
	}

	public void setTestProduct(String testProduct) {
		this.testProduct = testProduct;
	}
	
	
	
}
