package com.todaysoft.lims.system.model.searcher;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.todaysoft.lims.system.model.vo.TaskInput;

public class ProcessSearcher {

	private String taskName;

	private String orderCode;

	private Date createTaskTime;

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Date getCreateTaskTime() {
		return createTaskTime;
	}

	public void setCreateTaskTime(Date createTaskTime) {
		this.createTaskTime = createTaskTime;
	}

	
	
}
