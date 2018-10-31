package com.todaysoft.lims.technical.model.response;

import java.util.Map;

public class PhenoTypePointCallbackRequest {

	private String hpoJson;

	private Map<String, Object> status;

	private String taskId;

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getHpoJson() {
		return hpoJson;
	}

	public void setHpoJson(String hpoJson) {
		this.hpoJson = hpoJson;
	}

	public Map<String, Object> getStatus() {
		return status;
	}

	public void setStatus(Map<String, Object> status) {
		this.status = status;
	}

}
