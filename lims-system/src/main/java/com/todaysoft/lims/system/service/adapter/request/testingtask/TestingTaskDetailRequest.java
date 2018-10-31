package com.todaysoft.lims.system.service.adapter.request.testingtask;

public class TestingTaskDetailRequest {
	
	private String activitiTaskId;
	private String semantic;
	private String testingTaskId;
	private String inputId;
	private String sampleType;
	private String inputType;
	
	
	
	
	public String getInputType() {
		return inputType;
	}
	public void setInputType(String inputType) {
		this.inputType = inputType;
	}
	public String getInputId() {
		return inputId;
	}
	public void setInputId(String inputId) {
		this.inputId = inputId;
	}
	public String getSampleType() {
		return sampleType;
	}
	public void setSampleType(String sampleType) {
		this.sampleType = sampleType;
	}
	public String getSemantic() {
		return semantic;
	}
	public void setSemantic(String semantic) {
		this.semantic = semantic;
	}
	public String getActivitiTaskId() {
		return activitiTaskId;
	}
	public void setActivitiTaskId(String activitiTaskId) {
		this.activitiTaskId = activitiTaskId;
	}

	public String getTestingTaskId() {
		return testingTaskId;
	}

	public void setTestingTaskId(String testingTaskId) {
		this.testingTaskId = testingTaskId;
	}
}
