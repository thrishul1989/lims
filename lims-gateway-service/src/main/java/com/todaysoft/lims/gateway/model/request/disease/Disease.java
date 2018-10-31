package com.todaysoft.lims.gateway.model.request.disease;


import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.gateway.model.Phenotype;
import com.todaysoft.lims.gateway.model.ProductDisease;

public class Disease {

	

	private String id;
	private String code;   
	private String name;     
	private String nameEn;  //疾病英文名称 
	private String incidence;   //发病率
	private String onsetAge;       // '发病年龄',
	private String mutationType;      // 突变类型
	private String diseaseType;          // 疾病类型：字典-DISEASE_TYPE
	private String description;  //疾病简介
	private List<ProductDisease> productDiseaseList;
	
	
	

	private String hereditaryType;//遗传方式
	
	private List<DiseaseGene> geneList = new ArrayList<DiseaseGene>();

	private List<DiseasePhenotype> phenotypeList = new ArrayList<DiseasePhenotype>();
	

	public List<ProductDisease> getProductDiseaseList() {
		return productDiseaseList;
	}
	public void setProductDiseaseList(List<ProductDisease> productDiseaseList) {
		this.productDiseaseList = productDiseaseList;
	}

	private List<DiseaseAlias> alias = new ArrayList<DiseaseAlias>(); 
	
	
	
	private List<DiseaseHereditary> hereditaryList = new ArrayList<DiseaseHereditary>(); 
	
	
	
	public List<DiseaseHereditary> getHereditaryList() {
		return hereditaryList;
	}
	public void setHereditaryList(List<DiseaseHereditary> hereditaryList) {
		this.hereditaryList = hereditaryList;
	}
	public String getHereditaryType() {
		return hereditaryType;
	}
	public void setHereditaryType(String hereditaryType) {
		this.hereditaryType = hereditaryType;
	}
	
	public List<DiseaseGene> getGeneList() {
		return geneList;
	}
	public void setGeneList(List<DiseaseGene> geneList) {
		this.geneList = geneList;
	}
	public List<DiseasePhenotype> getPhenotypeList() {
		return phenotypeList;
	}
	public void setPhenotypeList(List<DiseasePhenotype> phenotypeList) {
		this.phenotypeList = phenotypeList;
	}
	public List<DiseaseAlias> getAlias() {
		return alias;
	}
	public void setAlias(List<DiseaseAlias> alias) {
		this.alias = alias;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getNameEn() {
		return nameEn;
	}
	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}
	public String getIncidence() {
		return incidence;
	}
	public void setIncidence(String incidence) {
		this.incidence = incidence;
	}
	public String getOnsetAge() {
		return onsetAge;
	}
	public void setOnsetAge(String onsetAge) {
		this.onsetAge = onsetAge;
	}
	public String getMutationType() {
		return mutationType;
	}
	public void setMutationType(String mutationType) {
		this.mutationType = mutationType;
	}
	public String getDiseaseType() {
		return diseaseType;
	}
	public void setDiseaseType(String diseaseType) {
		this.diseaseType = diseaseType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	

	

}
