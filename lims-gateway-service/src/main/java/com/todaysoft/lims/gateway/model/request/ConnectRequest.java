package com.todaysoft.lims.gateway.model.request;

public class ConnectRequest {

	private String connectNum;

	private Integer pageNo;
	private Integer pageSize;
	private String ids;
	private String id;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getConnectNum() {
		return connectNum;
	}

	public void setConnectNum(String connectNum) {
		this.connectNum = connectNum;
	}

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
	
	
}
