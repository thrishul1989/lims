package com.todaysoft.lims.sample.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.AutoKeyEntity;

@Entity
@Table(name = "LIMS_TESTING_SCHEDULE_ACTIVE")
public class TestingScheduleActive extends AutoKeyEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5436722698980660012L;

	private Integer scheduleId;
	
	private Integer testingTaskId;

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
}
