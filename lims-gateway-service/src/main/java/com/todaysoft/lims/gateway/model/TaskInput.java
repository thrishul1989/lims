package com.todaysoft.lims.gateway.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

public class TaskInput {


	private Integer id;

	private Task task;

	private Integer inputId;

	private String inputType;

	private Integer reagentId;
	
	private String inputQuantity;//默认输入量
    
    private String unit;//单位
    
    private Integer reagentKitId;//试剂盒
    
    private Integer taskId;
    
    

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public Integer getReagentKitId() {
		return reagentKitId;
	}

	public void setReagentKitId(Integer reagentKitId) {
		this.reagentKitId = reagentKitId;
	}

	public String getInputQuantity() {
		return inputQuantity;
	}

	public void setInputQuantity(String inputQuantity) {
		this.inputQuantity = inputQuantity;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

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
