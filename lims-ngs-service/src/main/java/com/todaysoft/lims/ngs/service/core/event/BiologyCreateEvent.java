package com.todaysoft.lims.ngs.service.core.event;

import java.util.Date;

public class BiologyCreateEvent {
	private String taskId;

	private String sequencingCode;

	private String sequencingType;

	private String runCode;

	private String laneCode;

	private Boolean ifUrgent;

	private Date urgentUpdateTime;

	private String urgentName;

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getSequencingCode() {
		return sequencingCode;
	}

	public void setSequencingCode(String sequencingCode) {
		this.sequencingCode = sequencingCode;
	}

	public String getSequencingType() {
		return sequencingType;
	}

	public void setSequencingType(String sequencingType) {
		this.sequencingType = sequencingType;
	}

	public String getRunCode() {
		return runCode;
	}

	public void setRunCode(String runCode) {
		this.runCode = runCode;
	}

	public String getLaneCode() {
		return laneCode;
	}

	public void setLaneCode(String laneCode) {
		this.laneCode = laneCode;
	}

	public Boolean getIfUrgent() {
		return ifUrgent;
	}

	public void setIfUrgent(Boolean ifUrgent) {
		this.ifUrgent = ifUrgent;
	}

	public Date getUrgentUpdateTime() {
		return urgentUpdateTime;
	}

	public void setUrgentUpdateTime(Date urgentUpdateTime) {
		this.urgentUpdateTime = urgentUpdateTime;
	}

	public String getUrgentName() {
		return urgentName;
	}

	public void setUrgentName(String urgentName) {
		this.urgentName = urgentName;
	}
}
