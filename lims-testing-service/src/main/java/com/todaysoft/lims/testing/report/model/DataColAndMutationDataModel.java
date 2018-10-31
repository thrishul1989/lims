package com.todaysoft.lims.testing.report.model;

import java.util.List;
import java.util.Map;

public class DataColAndMutationDataModel {
	private List<String> colList;
	private List<Map<String, String>> dataValue;
	private Map<String, List<String>> senondColName;

	private List<String> phenotypeList;

	private int matSumNumber;

	private int geneNumber;

	private int pageNo;

	private int pageSize;

	private int totalCount;

	public List<String> getColList() {
		return colList;
	}

	public void setColList(List<String> colList) {
		this.colList = colList;
	}

	public List<Map<String, String>> getDataValue() {
		return dataValue;
	}

	public void setDataValue(List<Map<String, String>> dataValue) {
		this.dataValue = dataValue;
	}

	public Map<String, List<String>> getSenondColName() {
		return senondColName;
	}

	public void setSenondColName(Map<String, List<String>> senondColName) {
		this.senondColName = senondColName;
	}

	public List<String> getPhenotypeList() {
		return phenotypeList;
	}

	public void setPhenotypeList(List<String> phenotypeList) {
		this.phenotypeList = phenotypeList;
	}

	public int getMatSumNumber() {
		return matSumNumber;
	}

	public void setMatSumNumber(int matSumNumber) {
		this.matSumNumber = matSumNumber;
	}

	public int getGeneNumber() {
		return geneNumber;
	}

	public void setGeneNumber(int geneNumber) {
		this.geneNumber = geneNumber;
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

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
}
