package com.todaysoft.lims.gateway.model.request.samplereceive;

import com.todaysoft.lims.gateway.model.SampleReceive;


public class SampleFormRequest extends SampleReceive{
	
	private String detailId;   //接收前台传递明细id

	public String getDetailId() {
		return detailId;
	}

	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}

}
