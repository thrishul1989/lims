package com.todaysoft.lims.ngs.model;

import java.util.List;

public class NgsSequencingSheetSubmitRequest {

	private String id;
	private String run;
	private List<String> lanCode;
	private String sequecingMachineCode;

	public String getSequecingMachineCode() {
		return sequecingMachineCode;
	}

	public void setSequecingMachineCode(String sequecingMachineCode) {
		this.sequecingMachineCode = sequecingMachineCode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRun() {
		return run;
	}

	public void setRun(String run) {
		this.run = run;
	}

	public List<String> getLanCode() {
		return lanCode;
	}

	public void setLanCode(List<String> lanCode) {
		this.lanCode = lanCode;
	}
}
