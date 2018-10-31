package com.todaysoft.lims.sample.entity;

import javax.persistence.*;

import com.todaysoft.lims.persist.AutoKeyEntity;

import java.io.Serializable;

@Entity
@Table(name = "LIMS_TESTING_TASK_RU_VARIABLE")
public class TestingTaskRunVariable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3295058467145290943L;

	private Integer testingTaskId;
	
	private String text;
	
	private String runVariables;

	@Id
	@Column(name="TASK_ID")
	public Integer getTestingTaskId() {
		return testingTaskId;
	}

	public void setTestingTaskId(Integer testingTaskId) {
		this.testingTaskId = testingTaskId;
	}

	@Column(name="TEXT")
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Column(name="RUN_VARIABLES")
	public String getRunVariables() {
		return runVariables;
	}

	public void setRunVariables(String runVariables) {
		this.runVariables = runVariables;
	}
}
