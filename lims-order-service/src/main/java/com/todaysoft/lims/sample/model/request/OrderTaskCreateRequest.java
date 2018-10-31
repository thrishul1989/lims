package com.todaysoft.lims.sample.model.request;

public class OrderTaskCreateRequest {

	private Integer orderId;
	private Integer sampleDetailId;
	private Integer taskId;
	private String taskName;
	private String inputType;
	private Integer inputKey;
	private String inputLocation;
	private Double inputAmount;
	private String inputUnit;
	private String outputType;
	private Integer outputKey;
	private String outputLocation;
	private Double outputAmount;
	private String outputUnit;

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getSampleDetailId() {
		return sampleDetailId;
	}

	public void setSampleDetailId(Integer sampleDetailId) {
		this.sampleDetailId = sampleDetailId;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getInputType() {
		return inputType;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	public Integer getInputKey() {
		return inputKey;
	}

	public void setInputKey(Integer inputKey) {
		this.inputKey = inputKey;
	}

	public String getInputLocation() {
		return inputLocation;
	}

	public void setInputLocation(String inputLocation) {
		this.inputLocation = inputLocation;
	}

	public Double getInputAmount() {
		return inputAmount;
	}

	public void setInputAmount(Double inputAmount) {
		this.inputAmount = inputAmount;
	}

	public String getInputUnit() {
		return inputUnit;
	}

	public void setInputUnit(String inputUnit) {
		this.inputUnit = inputUnit;
	}

	public String getOutputType() {
		return outputType;
	}

	public void setOutputType(String outputType) {
		this.outputType = outputType;
	}

	public Integer getOutputKey() {
		return outputKey;
	}

	public void setOutputKey(Integer outputKey) {
		this.outputKey = outputKey;
	}

	public String getOutputLocation() {
		return outputLocation;
	}

	public void setOutputLocation(String outputLocation) {
		this.outputLocation = outputLocation;
	}

	public Double getOutputAmount() {
		return outputAmount;
	}

	public void setOutputAmount(Double outputAmount) {
		this.outputAmount = outputAmount;
	}

	public String getOutputUnit() {
		return outputUnit;
	}

	public void setOutputUnit(String outputUnit) {
		this.outputUnit = outputUnit;
	}
}
