package com.todaysoft.lims.sample.model;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Task {

	private String id;

	private String semantic;

	private String taskType;

	private String description;

	private String name;

	private String outputSample;

	private BigDecimal outputVolume;

	private List<TaskInput> taskInputList = new ArrayList<TaskInput>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSemantic() {
		return semantic;
	}

	public void setSemantic(String semantic) {
		this.semantic = semantic;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOutputSample() {
		return outputSample;
	}

	public void setOutputSample(String outputSample) {
		this.outputSample = outputSample;
	}

	public BigDecimal getOutputVolume() {
		return outputVolume;
	}

	public void setOutputVolume(BigDecimal outputVolume) {
		this.outputVolume = outputVolume;
	}

	public List<TaskInput> getTaskInputList() {
		return taskInputList;
	}

	public void setTaskInputList(List<TaskInput> taskInputList) {
		this.taskInputList = taskInputList;
	}
}
