package com.todaysoft.lims.sample.model.request;

import com.todaysoft.lims.sample.entity.SampleReceive;

public class SampleFormRequest extends SampleReceive{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String detailId;   //接收前台传递明细id

	public String getDetailId() {
		return detailId;
	}

	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}

}
