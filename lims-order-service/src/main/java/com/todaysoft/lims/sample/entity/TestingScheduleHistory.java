package com.todaysoft.lims.sample.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.AutoKeyEntity;

@Entity
@Table(name = "LIMS_TESTING_SCHEDULE_HISTORY")
public class TestingScheduleHistory extends AutoKeyEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8469417109323895974L;
	
	private Integer scheduleId;
	
	private Integer testingTaskId;
	
	private Integer historyIndex;

	@Column(name="SCHEDULE_ID")
	public Integer getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(Integer scheduleId) {
		this.scheduleId = scheduleId;
	}

	@Column(name="TASK_ID")
	public Integer getTestingTaskId() {
		return testingTaskId;
	}

	public void setTestingTaskId(Integer testingTaskId) {
		this.testingTaskId = testingTaskId;
	}

	@Column(name="HISTORY_INDEX")
	public Integer getHistoryIndex() {
		return historyIndex;
	}

	public void setHistoryIndex(Integer historyIndex) {
		this.historyIndex = historyIndex;
	}
}
