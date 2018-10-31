package com.todaysoft.lims.sample.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "LIMS_TESTING_TASK_RESULT")
public class TestingTaskResult implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 256107762541801455L;

	private Integer testingTaskId;

	private Integer result;

	private Integer testerId;

	private String testerName;

	private String inputSample;

	private String inputReagentKit;

	private String outputSample;
	
	private String failureRemark;

	@Id
	@Column(name = "TASK_ID")
	public Integer getTestingTaskId() {
		return testingTaskId;
	}

	public void setTestingTaskId(Integer testingTaskId) {
		this.testingTaskId = testingTaskId;
	}

	@Column(name = "RESULT")
	public Integer getResult() {
		return result;
	}

	public void setResult(Integer result) {
		this.result = result;
	}

	@Column(name = "TESTER_ID")
	public Integer getTesterId() {
		return testerId;
	}

	public void setTesterId(Integer testerId) {
		this.testerId = testerId;
	}

	@Column(name = "TESTER_NAME")
	public String getTesterName() {
		return testerName;
	}

	public void setTesterName(String testerName) {
		this.testerName = testerName;
	}

	@Column(name = "INPUT_SAMPLE")
	public String getInputSample() {
		return inputSample;
	}

	public void setInputSample(String inputSample) {
		this.inputSample = inputSample;
	}

	@Column(name = "INPUT_REAGENT_KIT")
	public String getInputReagentKit() {
		return inputReagentKit;
	}

	public void setInputReagentKit(String inputReagentKit) {
		this.inputReagentKit = inputReagentKit;
	}

	@Column(name = "OUTPUT_SAMPLE")
	public String getOutputSample() {
		return outputSample;
	}

	public void setOutputSample(String outputSample) {
		this.outputSample = outputSample;
	}

	@Column(name = "FAILURE_REMARK")
	public String getFailureRemark() {
		return failureRemark;
	}

	public void setFailureRemark(String failureRemark) {
		this.failureRemark = failureRemark;
	}
}
