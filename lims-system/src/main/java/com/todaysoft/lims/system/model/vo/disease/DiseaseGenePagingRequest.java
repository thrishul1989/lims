package com.todaysoft.lims.system.model.vo.disease;

public class DiseaseGenePagingRequest extends DiseaseGeneFormRequest {
	
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
