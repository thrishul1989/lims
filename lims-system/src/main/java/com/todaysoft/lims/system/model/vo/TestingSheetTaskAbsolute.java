package com.todaysoft.lims.system.model.vo;

import java.util.List;

public class TestingSheetTaskAbsolute{

	private Integer id;
	
	private String sourceSampleCode;
	
	private List<TestingSheetTaskAbsoluteData> absoluteDataList;
	
	private Integer fragmentLength; // 片段长度
    
    private Double concentrationPC; // 上机浓度
    
    private Double cluster;
    
    private List<Double> wkSizeList;
	
	private List<String> unitConversionList;
	
	
    
    public List<Double> getWkSizeList() {
		return wkSizeList;
	}

	public void setWkSizeList(List<Double> wkSizeList) {
		this.wkSizeList = wkSizeList;
	}

	public List<String> getUnitConversionList() {
		return unitConversionList;
	}

	public void setUnitConversionList(List<String> unitConversionList) {
		this.unitConversionList = unitConversionList;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
    
	public String getSourceSampleCode() {
		return sourceSampleCode;
	}

	public void setSourceSampleCode(String sourceSampleCode) {
		this.sourceSampleCode = sourceSampleCode;
	}

	public List<TestingSheetTaskAbsoluteData> getAbsoluteDataList() {
		return absoluteDataList;
	}

	public void setAbsoluteDataList(List<TestingSheetTaskAbsoluteData> absoluteDataList) {
		this.absoluteDataList = absoluteDataList;
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
}
