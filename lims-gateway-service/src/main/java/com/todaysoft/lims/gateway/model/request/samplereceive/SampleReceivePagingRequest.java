package com.todaysoft.lims.gateway.model.request.samplereceive;

import com.todaysoft.lims.gateway.model.SampleReceive;


public class SampleReceivePagingRequest extends SampleReceive{

	/**
	 * 
	 */
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
	

}
