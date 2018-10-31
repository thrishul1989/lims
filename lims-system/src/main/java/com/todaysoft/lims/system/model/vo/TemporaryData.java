package com.todaysoft.lims.system.model.vo;


public class TemporaryData {
	
	private String sourceSampleCode;

	private Integer fragmentLength; // 片段长度
    
    private Double concentrationPC; // 上机浓度
    
    private Double cluster;
    
	private String quantifyInstrument; // 定量仪器

	private Double wkSize; // 文库量

	private String unitConversion; // 单位换算

	public String getSourceSampleCode() {
		return sourceSampleCode;
	}

	public void setSourceSampleCode(String sourceSampleCode) {
		this.sourceSampleCode = sourceSampleCode;
	}

	public Integer getFragmentLength() {
		return fragmentLength;
	}

	public void setFragmentLength(Integer fragmentLength) {
		this.fragmentLength = fragmentLength;
	}

	public Double getConcentrationPC() {
		return concentrationPC;
	}

	public void setConcentrationPC(Double concentrationPC) {
		this.concentrationPC = concentrationPC;
	}

	public Double getCluster() {
		return cluster;
	}

	public void setCluster(Double cluster) {
		this.cluster = cluster;
	}

	public String getQuantifyInstrument() {
		return quantifyInstrument;
	}

	public void setQuantifyInstrument(String quantifyInstrument) {
		this.quantifyInstrument = quantifyInstrument;
	}

	public Double getWkSize() {
		return wkSize;
	}

	public void setWkSize(Double wkSize) {
		this.wkSize = wkSize;
	}

	public String getUnitConversion() {
		return unitConversion;
	}

	public void setUnitConversion(String unitConversion) {
		this.unitConversion = unitConversion;
	}
	
	
}
