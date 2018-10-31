package com.todaysoft.lims.ngs.model.param;

import java.util.List;

public class NgsSequecingMachineMdReq {
	private String sequecingMachineId;

	private List<String> lanCode;

	public String getSequecingMachineId() {
		return sequecingMachineId;
	}

	public void setSequecingMachineId(String sequecingMachineId) {
		this.sequecingMachineId = sequecingMachineId;
	}

	public List<String> getLanCode() {
		return lanCode;
	}

	public void setLanCode(List<String> lanCode) {
		this.lanCode = lanCode;
	}
}
