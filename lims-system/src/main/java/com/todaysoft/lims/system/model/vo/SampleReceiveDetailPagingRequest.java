package com.todaysoft.lims.system.model.vo;

public class SampleReceiveDetailPagingRequest {
	
	
	private String sampleIdentification;//样本标识
	
	private String state; //状态： 1未启动、2启动
	
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

	public String getSampleIdentification() {
		return sampleIdentification;
	}

	public void setSampleIdentification(String sampleIdentification) {
		this.sampleIdentification = sampleIdentification;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	
}
