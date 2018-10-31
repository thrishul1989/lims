package com.todaysoft.lims.gateway.model.request;


import com.todaysoft.lims.gateway.model.TaskInput;

import java.util.List;

/**
 * Created by HSHY-032 on 2016/6/8.
 */
public class TaskPagingRequest {

    private String id;

    private String taskCode;

    private String taskName;

    private String taskType;

    private String taskDescription;
    
    private Integer owner;
	
	private String  taskTypeName;

    private Integer taskOutput;

    private List<TaskInput> taskInputList;

    private int pageNo;

    private int pageSize;
    
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

	public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
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
