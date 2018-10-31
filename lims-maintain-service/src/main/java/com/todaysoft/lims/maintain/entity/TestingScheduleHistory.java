package com.todaysoft.lims.maintain.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_TESTING_SCHEDULE_HISTORY")
public class TestingScheduleHistory extends UuidKeyEntity {
	private static final long serialVersionUID = -968558231755149297L;

	private String scheduleId;

	private String taskId;

	private Date timestamp;

	private String taskRefer;// 任务id从属 表

	@Column(name = "TASK_REFER")
	public String getTaskRefer() {
		return taskRefer;
	}

	public void setTaskRefer(String taskRefer) {
		this.taskRefer = taskRefer;
	}

	@Column(name = "TASK_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
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
}
