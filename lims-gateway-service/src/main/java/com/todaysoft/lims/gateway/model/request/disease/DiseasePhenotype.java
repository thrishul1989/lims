package com.todaysoft.lims.gateway.model.request.disease;

import com.todaysoft.lims.gateway.model.Phenotype;



public class DiseasePhenotype {

	
	
	private String id;
	private String dependency;
	private Phenotype phenotype;
	private Disease disease;
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDependency() {
		return dependency;
	}

	public void setDependency(String dependency) {
		this.dependency = dependency;
	}

	public Disease getDisease() {
		return disease;
	}

	public void setDisease(Disease disease) {
		this.disease = disease;
	}
	
	
	
	public Phenotype getPhenotype() {
		return phenotype;
	}

	public void setPhenotype(Phenotype phenotype) {
		this.phenotype = phenotype;
	}



	
	

}
