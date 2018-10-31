package com.todaysoft.lims.technical.entity;

import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection ="analysis-capcnv")
public class CapCnvData extends IdEntity {

	private String _id;

	private String analysisSampleId;// 待分析样本 的记录ID

	private String chrLocation;

	private String gene;

	private String area;

	private Double copyNumber;

	private Double pValue;

	private String analysisFamilyId;

	@Transient
	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getAnalysisFamilyId() {
		return analysisFamilyId;
	}

	public void setAnalysisFamilyId(String analysisFamilyId) {
		this.analysisFamilyId = analysisFamilyId;
	}

	public String getAnalysisSampleId() {
		return analysisSampleId;
	}

	public void setAnalysisSampleId(String analysisSampleId) {
		this.analysisSampleId = analysisSampleId;
	}

	public String getChrLocation() {
		return chrLocation;
	}

	public void setChrLocation(String chrLocation) {
		this.chrLocation = chrLocation;
	}

	public String getGene() {
		return gene;
	}

	public void setGene(String gene) {
		this.gene = gene;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Double getCopyNumber() {
		return copyNumber;
	}

	public void setCopyNumber(Double copyNumber) {
		this.copyNumber = copyNumber;
	}

	public Double getpValue() {
		return pValue;
	}

	public void setpValue(Double pValue) {
		this.pValue = pValue;
	}
}
