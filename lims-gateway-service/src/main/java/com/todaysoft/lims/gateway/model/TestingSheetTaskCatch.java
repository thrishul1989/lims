package com.todaysoft.lims.gateway.model;

import com.todaysoft.lims.gateway.model.request.samplereceive.SampleReceiveDetail;

public class TestingSheetTaskCatch{

	private Integer id;
	private TestingSheetTask sheetTask;
	private String activitiTaskId;
	private String inputSampleCode;
	private Double sampleVolume; //混样体积
	private Double concn; //文库浓度
	private String indexCode;
	private String sampleName;
	private String inputSmapleLocation;
	private SampleReceiveDetail sampleDetail;
	private Integer productId;
	private Integer probeId;
	
	
	public Integer getProbeId() {
		return probeId;
	}

	public void setProbeId(Integer probeId) {
		this.probeId = probeId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TestingSheetTask getSheetTask() {
		return sheetTask;
	}

	public void setSheetTask(TestingSheetTask sheetTask) {
		this.sheetTask = sheetTask;
	}

	public String getActivitiTaskId() {
		return activitiTaskId;
	}

	public void setActivitiTaskId(String activitiTaskId) {
		this.activitiTaskId = activitiTaskId;
	}

	public String getInputSampleCode() {
		return inputSampleCode;
	}

	public void setInputSampleCode(String inputSampleCode) {
		this.inputSampleCode = inputSampleCode;
	}

	public Double getSampleVolume() {
		return sampleVolume;
	}

	public void setSampleVolume(Double sampleVolume) {
		this.sampleVolume = sampleVolume;
	}

	public Double getConcn() {
		return concn;
	}

	public void setConcn(Double concn) {
		this.concn = concn;
	}

	public String getIndexCode() {
		return indexCode;
	}

	public void setIndexCode(String indexCode) {
		this.indexCode = indexCode;
	}

	public String getSampleName() {
		return sampleName;
	}

	public void setSampleName(String sampleName) {
		this.sampleName = sampleName;
	}

	public String getInputSmapleLocation() {
		return inputSmapleLocation;
	}

	public void setInputSmapleLocation(String inputSmapleLocation) {
		this.inputSmapleLocation = inputSmapleLocation;
	}

	public SampleReceiveDetail getSampleDetail() {
		return sampleDetail;
	}

	public void setSampleDetail(SampleReceiveDetail sampleDetail) {
		this.sampleDetail = sampleDetail;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}
}
