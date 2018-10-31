package com.todaysoft.lims.technical.model.request;


public class CapCnvRequest  {
    
    private String dataCode;

    private String analysisSampleId;

    private Double startCopyNumber;

    private Double endCopyNumber;

    private Double startPValue;

    private Double endPValue;

    private String gene;

    private String sampleCode;
    
    private Integer pageNo;
    
    private Integer pageSize;

    public String getDataCode()
    {
        return dataCode;
    }

    public void setDataCode(String dataCode)
    {
        this.dataCode = dataCode;
    }
    
	public String getSampleCode() {
        return sampleCode;
    }

    public void setSampleCode(String sampleCode) {
        this.sampleCode = sampleCode;
    }

    public String getAnalysisSampleId() {
        return analysisSampleId;
    }

    public void setAnalysisSampleId(String analysisSampleId) {
        this.analysisSampleId = analysisSampleId;
    }

    public Double getStartCopyNumber() {
        return startCopyNumber;
    }

    public void setStartCopyNumber(Double startCopyNumber) {
        this.startCopyNumber = startCopyNumber;
    }

    public Double getEndCopyNumber() {
        return endCopyNumber;
    }

    public void setEndCopyNumber(Double endCopyNumber) {
        this.endCopyNumber = endCopyNumber;
    }

    public Double getStartPValue() {
        return startPValue;
    }

    public void setStartPValue(Double startPValue) {
        this.startPValue = startPValue;
    }

    public Double getEndPValue() {
        return endPValue;
    }

    public void setEndPValue(Double endPValue) {
        this.endPValue = endPValue;
    }

    public String getGene() {
        return gene;
    }

    public void setGene(String gene) {
        this.gene = gene;
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
