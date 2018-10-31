package com.todaysoft.lims.ngs.model;

import java.util.List;

public class NgsSequecingMachine {
	private String id;

	private String sequecingMachineCode;

	private List<NgsSequecingLan> lanList;

	public List<NgsSequecingLan> getLanList() {
		return lanList;
	}

	public void setLanList(List<NgsSequecingLan> lanList) {
		this.lanList = lanList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSequecingMachineCode() {
		return sequecingMachineCode;
	}

	public void setSequecingMachineCode(String sequecingMachineCode) {
		this.sequecingMachineCode = sequecingMachineCode;
	}

	
}