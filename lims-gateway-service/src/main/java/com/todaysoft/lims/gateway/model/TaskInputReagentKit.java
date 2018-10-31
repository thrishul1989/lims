package com.todaysoft.lims.gateway.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

public class TaskInputReagentKit 
{
    
	private Integer  id;

    private TaskInput taskInput;
    
    private double inputQuantity;//默认输入量
    
    private String unit;//单位
   
    private Integer reagentKitId;//试剂盒
    

    private String  reagentName;
    
    private String  reagentCode;
    
    
    
    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getReagentName() {
		return reagentName;
	}

	public void setReagentName(String reagentName) {
		this.reagentName = reagentName;
	}

	public String getReagentCode() {
		return reagentCode;
	}

	public void setReagentCode(String reagentCode) {
		this.reagentCode = reagentCode;
	}

	@JsonIgnore
	public TaskInput getTaskInput() {
		return taskInput;
	}

	public void setTaskInput(TaskInput taskInput) {
		this.taskInput = taskInput;
	}

	public double getInputQuantity() {
		return inputQuantity;
	}

	public void setInputQuantity(double inputQuantity) {
		this.inputQuantity = inputQuantity;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Integer getReagentKitId() {
		return reagentKitId;
	}

	public void setReagentKitId(Integer reagentKitId) {
		this.reagentKitId = reagentKitId;
	}
    
  
}
