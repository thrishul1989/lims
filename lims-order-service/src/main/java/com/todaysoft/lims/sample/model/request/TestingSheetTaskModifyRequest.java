package com.todaysoft.lims.sample.model.request;

import com.todaysoft.lims.sample.entity.TestingSheet;
import com.todaysoft.lims.sample.entity.TestingSheetTaskResult;

public class TestingSheetTaskModifyRequest {
	
	private Integer id;
	
	private TestingSheet testingSheet;
	
	private Integer previousSheetTaskId; //上一步的任务id

	private String activitiTaskId; //工作流任务id
	
	private String semantic;
	
	private String sampleType; //原始样本名称

	private String testingCode; //实验编号

	private String inputSampleInstanceId;
	
	private String inputSamplecode;

	private String inputSampleLocation;

	private String outputSamplecode;

	private String outputSampleLocation;

	private Integer testingScheduleId; //scheduleId 用于跟踪流程
	
	private TestingSheetTaskResult taskResult;
	
	private Integer connectorId; //接头编号（用于文库构建时）
	
	private String remark; //备注

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TestingSheet getTestingSheet() {
		return testingSheet;
	}

	public void setTestingSheet(TestingSheet testingSheet) {
		this.testingSheet = testingSheet;
	}

	public Integer getPreviousSheetTaskId() {
		return previousSheetTaskId;
	}

	public void setPreviousSheetTaskId(Integer previousSheetTaskId) {
		this.previousSheetTaskId = previousSheetTaskId;
	}

	public String getActivitiTaskId() {
		return activitiTaskId;
	}

	public void setActivitiTaskId(String activitiTaskId) {
		this.activitiTaskId = activitiTaskId;
	}

	public String getSemantic() {
		return semantic;
	}

	public void setSemantic(String semantic) {
		this.semantic = semantic;
	}

	public String getSampleType() {
		return sampleType;
	}

	public void setSampleType(String sampleType) {
		this.sampleType = sampleType;
	}

	public String getTestingCode() {
		return testingCode;
	}

	public void setTestingCode(String testingCode) {
		this.testingCode = testingCode;
	}

	public String getInputSampleInstanceId() {
		return inputSampleInstanceId;
	}

	public void setInputSampleInstanceId(String inputSampleInstanceId) {
		this.inputSampleInstanceId = inputSampleInstanceId;
	}

	public String getInputSamplecode() {
		return inputSamplecode;
	}

	public void setInputSamplecode(String inputSamplecode) {
		this.inputSamplecode = inputSamplecode;
	}

	public String getInputSampleLocation() {
		return inputSampleLocation;
	}

	public void setInputSampleLocation(String inputSampleLocation) {
		this.inputSampleLocation = inputSampleLocation;
	}

	public String getOutputSamplecode() {
		return outputSamplecode;
	}

	public void setOutputSamplecode(String outputSamplecode) {
		this.outputSamplecode = outputSamplecode;
	}

	public String getOutputSampleLocation() {
		return outputSampleLocation;
	}

	public void setOutputSampleLocation(String outputSampleLocation) {
		this.outputSampleLocation = outputSampleLocation;
	}

	public Integer getTestingScheduleId() {
		return testingScheduleId;
	}

	public void setTestingScheduleId(Integer testingScheduleId) {
		this.testingScheduleId = testingScheduleId;
	}

	public TestingSheetTaskResult getTaskResult() {
		return taskResult;
	}

	public void setTaskResult(TestingSheetTaskResult taskResult) {
		this.taskResult = taskResult;
	}

	public Integer getConnectorId() {
		return connectorId;
	}

	public void setConnectorId(Integer connectorId) {
		this.connectorId = connectorId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
