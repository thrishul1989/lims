package com.todaysoft.lims.system.modules.bpm.ontest.model;

public class NgsSequecingMonitorRequest {
	private String sequecingCode;
	private String timeStart;
	private String timeEnd;
	private Integer ifUrgent;

	public String getSequecingCode() {
		return sequecingCode;
	}

	public void setSequecingCode(String sequecingCode) {
		this.sequecingCode = sequecingCode;
	}

	public String getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(String timeStart) {
		this.timeStart = timeStart;
	}

	public String getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}

	public Integer getIfUrgent() {
		return ifUrgent;
	}

	public void setIfUrgent(Integer ifUrgent) {
		this.ifUrgent = ifUrgent;
	}
}
