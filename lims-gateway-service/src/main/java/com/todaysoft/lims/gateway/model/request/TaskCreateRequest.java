package com.todaysoft.lims.gateway.model.request;


import com.todaysoft.lims.gateway.model.TaskInput;
import com.todaysoft.lims.gateway.model.TaskReagentKit;

import java.util.List;

/**
 * Created by HSHY-032 on 2016/6/8.
 */
public class TaskCreateRequest {

    private Integer id;

    private String taskName;

    private String taskType;

    private String taskDescription;

    private String outputType;

    private Integer outputId;

    private String inputContent;
    
    private Integer owner;
	
	private String  taskTypeName;
	
	private double outputQuantity;
	
	private String unit;
	
	private String reagentKit;//试剂盒
	
	private List<TaskReagentKit> trkList;
	
	 private String processStage;//l流程阶段 1 准备 2 实验 3 数据上传
	    
	public String getProcessStage() {
		return processStage;
	}

	public void setProcessStage(String processStage) {
		this.processStage = processStage;
	}

    public List<TaskReagentKit> getTrkList() {
		return trkList;
	}

	public void setTrkList(List<TaskReagentKit> trkList) {
		this.trkList = trkList;
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
