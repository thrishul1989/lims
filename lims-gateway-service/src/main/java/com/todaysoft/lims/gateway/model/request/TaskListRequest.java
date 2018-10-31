package com.todaysoft.lims.gateway.model.request;


import com.todaysoft.lims.gateway.model.TaskInput;

import java.util.List;
import java.util.Set;

/**
 * Created by HSHY-032 on 2016/6/8.
 */
public class TaskListRequest {

    private Integer id;

    private String taskCode;

    private String taskName;

    private String taskType;

    private String taskDescription;

    private Integer taskOutput;

    private String inputType;

    private String input;
    
    private Integer owner;
	
	private String  taskTypeName;

    private List<TaskInput> taskInputList;

    private Set<Integer> ids;
    
   private double outputQuantity;
    
    private String unit;
    
    private String reagentKit;//试剂盒
    
    private String processStage;//l流程阶段 1 准备 2 实验 3 数据上传
    
   	public String getProcessStage() {
   		return processStage;
   	}

   	public void setProcessStage(String processStage) {
   		this.processStage = processStage;
   	}

	public String getReagentKit() {
		return reagentKit;
	}

	public void setReagentKit(String reagentKit) {
		this.reagentKit = reagentKit;
	}

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

    public String getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
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

    public Integer getTaskOutput() {
        return taskOutput;
    }

    public void setTaskOutput(Integer taskOutput) {
        this.taskOutput = taskOutput;
    }

    public List<TaskInput> getTaskInputList() {
        return taskInputList;
    }

    public void setTaskInputList(List<TaskInput> taskInputList) {
        this.taskInputList = taskInputList;
    }

    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public Set<Integer> getIds() {
        return ids;
    }

    public void setIds(Set<Integer> ids) {
        this.ids = ids;
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
    
    
}
