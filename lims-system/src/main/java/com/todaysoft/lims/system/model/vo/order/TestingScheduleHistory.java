package com.todaysoft.lims.system.model.vo.order;

import java.util.Date;

public class TestingScheduleHistory {
	private String id;

	private String scheduleId;

	private String taskId;

	private Date timestamp;

	private Date pauseTime;

	private String pauseName;

	private Date restartTime;

	private String restartName;

	private String remark;

	private String taskRefer;

	public String getTaskRefer() {
		return taskRefer;
	}

	public void setTaskRefer(String taskRefer) {
		this.taskRefer = taskRefer;
	}

	public Date getPauseTime() {
		return pauseTime;
	}

	public void setPauseTime(Date pauseTime) {
		this.pauseTime = pauseTime;
	}

	public String getPauseName() {
		return pauseName;
	}

	public void setPauseName(String pauseName) {
		this.pauseName = pauseName;
	}

	public Date getRestartTime() {
		return restartTime;
	}

	public void setRestartTime(Date restartTime) {
		this.restartTime = restartTime;
	}

	public String getRestartName() {
		return restartName;
	}

	public void setRestartName(String restartName) {
		this.restartName = restartName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
