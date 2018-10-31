package com.todaysoft.lims.system.model.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class Todo {
	
	private Integer id;

    private String orderCode;
    
	private String taskName;
	
	private Date createTaskTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Date getCreateTaskTime() {
		return createTaskTime;
	}

	public void setCreateTaskTime(Date createTaskTime) {
		this.createTaskTime = createTaskTime;
	}
	
	
}
