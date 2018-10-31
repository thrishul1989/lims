package com.todaysoft.lims.gateway.model;


import java.util.ArrayList;
import java.util.List;

public class Task {

	private Integer id;

	private String taskName;

	private String taskCode;

	private String taskType;

	private String taskDescription;

	private String outputType;

	private Integer outputId;

	private String inputContent;

	private Integer owner;
	
	private String  taskTypeName;
	
	private List<TaskInput> taskInputList = new ArrayList<TaskInput>();
	
	private String reagentKit;//试剂盒
	
	 private String processStage;//l流程阶段 1 准备 2 实验 3 数据上传
	    
	public String getProcessStage() {
		return processStage;
	}

	public void setProcessStage(String processStage) {
		this.processStage = processStage;
	}

	 
    private double outputQuantity;
    
    private String unit;
    
    
	
	public double getOutputQuantity() {
		return outputQuantity;
	}

	public void setOutputQuantity(double outputQuantity) {
		this.outputQuantity = outputQuantity;
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

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getTaskDescription() {
		return taskDescription;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	public String getOutputType() {
		return outputType;
	}

	public void setOutputType(String outputType) {
		this.outputType = outputType;
	}

	public Integer getOutputId() {
		return outputId;
	}

	public void setOutputId(Integer outputId) {
		this.outputId = outputId;
	}

	public String getInputContent() {
		return inputContent;
	}

	public void setInputContent(String inputContent) {
		this.inputContent = inputContent;
	}

	public List<TaskInput> getTaskInputList() {
		return taskInputList;
	}

	public void setTaskInputList(List<TaskInput> taskInputList) {
		this.taskInputList = taskInputList;
	}

	public Integer getOwner() {
		return owner;
	}

	public void setOwner(Integer owner) {
		this.owner = owner;
	}

	public String getTaskTypeName() {
		return taskTypeName;
	}

	public void setTaskTypeName(String taskTypeName) {
		this.taskTypeName = taskTypeName;
	}

	public String getReagentKit() {
		return reagentKit;
	}

	public void setReagentKit(String reagentKit) {
		this.reagentKit = reagentKit;
	}
	
	
	
}
