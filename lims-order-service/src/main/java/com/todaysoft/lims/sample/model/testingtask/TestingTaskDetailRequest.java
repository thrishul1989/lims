package com.todaysoft.lims.sample.model.testingtask;

public class TestingTaskDetailRequest {

	private String activitiTaskId;
	private String semantic;
	private String testingTaskId;

	public String getActivitiTaskId() {
		return activitiTaskId;
	}

	public void setActivitiTaskId(String activitiTaskId) {
		this.activitiTaskId = activitiTaskId;
	}

	public String getSemantic() {
		return semantic;
	}

	public void setSemantic(String semantic) {
		this.semantic = semantic;
	}

	public String getTestingTaskId() {
		return testingTaskId;
	}

	public void setTestingTaskId(String testingTaskId) {
		this.testingTaskId = testingTaskId;
	}
}
