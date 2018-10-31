package com.todaysoft.lims.gateway.model.request.testingtask;

public class SampleTracingPaging {

	private Integer sampleInstanceId;
	private Integer productId;
	private int pageNo;
	private int pageSize;

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getSampleInstanceId() {
		return sampleInstanceId;
	}

	public void setSampleInstanceId(Integer sampleInstanceId) {
		this.sampleInstanceId = sampleInstanceId;
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

}
