package com.todaysoft.lims.technical.model;

import java.util.List;

import com.mongodb.BasicDBObject;

public class MutationFilterToWarpDataModel {

	private String templateId;
	private List<String> diseaseNameList;
	private List<String> geneSymbolList;
	private List<String> phenotypeList;
	private BasicDBObject searchCond;

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public List<String> getDiseaseNameList() {
		return diseaseNameList;
	}

	public void setDiseaseNameList(List<String> diseaseNameList) {
		this.diseaseNameList = diseaseNameList;
	}

	public List<String> getGeneSymbolList() {
		return geneSymbolList;
	}

	public void setGeneSymbolList(List<String> geneSymbolList) {
		this.geneSymbolList = geneSymbolList;
	}

	public List<String> getPhenotypeList() {
		return phenotypeList;
	}

	public void setPhenotypeList(List<String> phenotypeList) {
		this.phenotypeList = phenotypeList;
	}

	public BasicDBObject getSearchCond() {
		return searchCond;
	}

	public void setSearchCond(BasicDBObject searchCond) {
		this.searchCond = searchCond;
	}
}
