package com.todaysoft.lims.schedule.service.core.event;


public class ScheduleTaskActiveEvent {

	private String taskId;
	private String taskRefer;
	private String preTaskId;

	private String dataCode;

	public String getPreTaskId() {
		return preTaskId;
	}

	public void setPreTaskId(String preTaskId) {
		this.preTaskId = preTaskId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskRefer() {
		return taskRefer;
	}

	public void setTaskRefer(String taskRefer) {
		this.taskRefer = taskRefer;
	}

	public String getDataCode() {
		return dataCode;
	}

	public void setDataCode(String dataCode) {
		this.dataCode = dataCode;
	}
}
