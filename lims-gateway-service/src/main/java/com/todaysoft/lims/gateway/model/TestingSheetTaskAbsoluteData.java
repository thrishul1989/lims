package com.todaysoft.lims.gateway.model;

public class TestingSheetTaskAbsoluteData {

	private Integer id;

	private TestingSheetTaskAbsolute absolute;

	private TestingSheetTaskResult sheetTaskResult;

	private String quantifyInstrument; // 定量仪器

	private Double wkSize; // 文库量

	private String unitConversion; // 单位换算

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TestingSheetTaskAbsolute getAbsolute() {
		return absolute;
	}

	public void setAbsolute(TestingSheetTaskAbsolute absolute) {
		this.absolute = absolute;
	}

	public TestingSheetTaskResult getSheetTaskResult() {
		return sheetTaskResult;
	}

	public void setSheetTaskResult(TestingSheetTaskResult sheetTaskResult) {
		this.sheetTaskResult = sheetTaskResult;
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
