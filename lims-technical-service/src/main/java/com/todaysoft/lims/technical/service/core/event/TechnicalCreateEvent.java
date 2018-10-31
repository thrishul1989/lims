package com.todaysoft.lims.technical.service.core.event;

public class TechnicalCreateEvent {

	private String biologyTaskId;

	private String dataCode; // 数据编号

	private String lanCode; // lan号

	private String sequencingCode; // 测序编号
	
	private String familyId;// 家系id

	public String getFamilyId() {
		return familyId;
	}

	public void setFamilyId(String familyId) {
		this.familyId = familyId;
	}

	public String getDataCode() {
		return dataCode;
	}

	public void setDataCode(String dataCode) {
		this.dataCode = dataCode;
	}

	public String getLanCode() {
		return lanCode;
	}

	public void setLanCode(String lanCode) {
		this.lanCode = lanCode;
	}

	public String getSequencingCode() {
		return sequencingCode;
	}

	public void setSequencingCode(String sequencingCode) {
		this.sequencingCode = sequencingCode;
	}

	public String getBiologyTaskId() {
		return biologyTaskId;
	}

	public void setBiologyTaskId(String biologyTaskId) {
		this.biologyTaskId = biologyTaskId;
	}

}
