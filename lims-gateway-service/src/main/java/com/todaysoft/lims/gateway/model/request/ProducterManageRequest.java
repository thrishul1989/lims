package com.todaysoft.lims.gateway.model.request;

public class ProducterManageRequest
{
	private String producterNo;
	
	private String state;
	
	private Integer pageNo;
	
	private Integer pageSize;


	public Integer getPageNo() {
		return pageNo;
	}
	
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	
	public Integer getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getProducterNo() {
		return producterNo;
	}

	public void setProducterNo(String producterNo) {
		this.producterNo = producterNo;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	
}
