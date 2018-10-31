package com.todaysoft.lims.sample.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

public class TaskInput {


	private Integer id;

	private Task task;

	private Integer inputId;

	private String inputType;

	private Integer reagentId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@JsonIgnore
	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public Integer getInputId() {
		return inputId;
	}

	public void setInputId(Integer inputId) {
		this.inputId = inputId;
	}

	public String getInputType() {
		return inputType;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	public Integer getReagentId() {
		return reagentId;
	}

	public void setReagentId(Integer reagentId) {
		this.reagentId = reagentId;
	}
}
