package com.todaysoft.lims.system.model.vo.disease;


public class DiseaseHereditary {
	
	

	private String id;
	private String hereditaryType;     
	
	private Disease disease;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHereditaryType() {
		return hereditaryType;
	}

	public void setHereditaryType(String hereditaryType) {
		this.hereditaryType = hereditaryType;
	}

	public Disease getDisease() {
		return disease;
	}

	public void setDisease(Disease disease) {
		this.disease = disease;
	}

	
	

}
