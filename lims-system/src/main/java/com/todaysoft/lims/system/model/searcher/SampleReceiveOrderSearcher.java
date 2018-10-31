package com.todaysoft.lims.system.model.searcher;

public class SampleReceiveOrderSearcher {
	private String orderCode; //订单编号
	
	private String testProduct;//检测产品
	
	private int pageNo;
	
	private int pageSize;
	
	
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
