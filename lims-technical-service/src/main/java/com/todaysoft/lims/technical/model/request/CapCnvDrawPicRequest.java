package com.todaysoft.lims.technical.model.request;



public class CapCnvDrawPicRequest {

    private Integer pageNo;
    
    private Integer pageSize;
	
    private String sampleAnalysisId;

    private String ids;

    private String sampleCode;
    

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

	public String getSampleAnalysisId() {
        return sampleAnalysisId;
    }

    public void setSampleAnalysisId(String sampleAnalysisId) {
        this.sampleAnalysisId = sampleAnalysisId;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getSampleCode() {
        return sampleCode;
    }

    public void setSampleCode(String sampleCode) {
        this.sampleCode = sampleCode;
    }
}
