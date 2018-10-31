package com.todaysoft.lims.ngs.model;

import java.util.List;



public class TestingSheetRequest {
	private String taskName;

	private String code;

	private String assignerName;

	private String testerName;

	private int pageNo;

	private int pageSize;

	private String assignTimeStart;

	private String assignTimeEnd;

	private String sampleCode;

	private List<String> childSampleCodes;

	public List<String> getChildSampleCodes() {
		return childSampleCodes;
	}

	public void setChildSampleCodes(List<String> childSampleCodes) {
		this.childSampleCodes = childSampleCodes;
	}

	private String completed;

	public String getCompleted() {
		return completed;
	}

	public void setCompleted(String completed) {
		this.completed = completed;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAssignerName() {
		return assignerName;
	}

	public void setAssignerName(String assignerName) {
		this.assignerName = assignerName;
	}

	public String getTesterName() {
		return testerName;
	}

	public void setTesterName(String testerName) {
		this.testerName = testerName;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getAssignTimeStart() {
		return assignTimeStart;
	}

	public void setAssignTimeStart(String assignTimeStart) {
		this.assignTimeStart = assignTimeStart;
	}

	public String getAssignTimeEnd() {
		return assignTimeEnd;
	}

	public void setAssignTimeEnd(String assignTimeEnd) {
		this.assignTimeEnd = assignTimeEnd;
	}

	public String getSampleCode() {
		return sampleCode;
	}

	public void setSampleCode(String sampleCode) {
		this.sampleCode = sampleCode;
	}
}
