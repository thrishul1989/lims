package com.todaysoft.lims.gateway.model.request.testingtask;

public class TestingTaskResult{

	private Integer testingTaskId;

	private Integer result;

	private Integer testerId;

	private String testerName;

	private String inputSample;

	private String inputReagentKit;

	private String outputSample;
	
	private String failureRemark;

	public Integer getTestingTaskId() {
		return testingTaskId;
	}

	public void setTestingTaskId(Integer testingTaskId) {
		this.testingTaskId = testingTaskId;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	public Integer getTesterId() {
		return testerId;
	}

	public void setTesterId(Integer testerId) {
		this.testerId = testerId;
	}

	public String getTesterName() {
		return testerName;
	}

	public void setTesterName(String testerName) {
		this.testerName = testerName;
	}

	public String getInputSample() {
		return inputSample;
	}

	public void setInputSample(String inputSample) {
		this.inputSample = inputSample;
	}

	public String getInputReagentKit() {
		return inputReagentKit;
	}

	public void setInputReagentKit(String inputReagentKit) {
		this.inputReagentKit = inputReagentKit;
	}

	public String getOutputSample() {
		return outputSample;
	}

	public void setOutputSample(String outputSample) {
		this.outputSample = outputSample;
	}

	public String getFailureRemark() {
		return failureRemark;
	}

	public void setFailureRemark(String failureRemark) {
		this.failureRemark = failureRemark;
	}
}
