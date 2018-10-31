package com.todaysoft.lims.schedule.entity;

import com.todaysoft.lims.persist.UuidKeyEntity;

import javax.persistence.*;

import java.util.Date;

@Entity
@Table(name = "LIMS_TESTING_SCHEDULE_HISTORY")
public class TestingScheduleHistory extends UuidKeyEntity {
	private static final long serialVersionUID = -968558231755149297L;

	private String scheduleId;

	private String taskId;

	private Date timestamp;

	private Date pauseTime;

	private String pauseName;

	private Date restartTime;

	private String restartName;
	private String taskRefer;// 任务id从属 表

	@Column(name = "TASK_REFER")
	public String getTaskRefer() {
		return taskRefer;
	}

	public void setTaskRefer(String taskRefer) {
		this.taskRefer = taskRefer;
	}

	@Column(name = "PAUSE_TIME")
	public Date getPauseTime() {
		return pauseTime;
	}

	public void setPauseTime(Date pauseTime) {
		this.pauseTime = pauseTime;
	}

	@Column(name = "PAUSE_NAME")
	public String getPauseName() {
		return pauseName;
	}

	public void setPauseName(String pauseName) {
		this.pauseName = pauseName;
	}

	@Column(name = "RESTART_TIME")
	public Date getRestartTime() {
		return restartTime;
	}

	public void setRestartTime(Date restartTime) {
		this.restartTime = restartTime;
	}

	@Column(name = "RESTART_NAME")
	public String getRestartName() {
		return restartName;
	}

	public void setRestartName(String restartName) {
		this.restartName = restartName;
	}

	@Column(name = "SCHEDULE_ID")
	public String getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}

	@Column(name = "TASK_ID")
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	@Column(name = "TASK_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
}
