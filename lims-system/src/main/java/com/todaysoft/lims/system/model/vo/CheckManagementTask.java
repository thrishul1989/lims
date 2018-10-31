package com.todaysoft.lims.system.model.vo;


public class CheckManagementTask {
	
	private CheckManagement checkManagement;// 检测管理
	
	private Integer taskId;// 检测ID
	
	private Integer taskOrder;// 检测顺序
	
	public CheckManagement getCheckManagement() {
		return checkManagement;
	}
	public void setCheckManagement(CheckManagement checkManagement) {
		this.checkManagement = checkManagement;
	}
	public Integer getTaskId() {
		return taskId;
	}
	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
	public Integer getTaskOrder() {
		return taskOrder;
	}
	public void setTaskOrder(Integer taskOrder) {
		this.taskOrder = taskOrder;
	}

	
}
