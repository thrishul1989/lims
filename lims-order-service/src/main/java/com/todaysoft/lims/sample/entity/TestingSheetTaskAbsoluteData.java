package com.todaysoft.lims.sample.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.AutoKeyEntity;

@Entity
@Table(name = "LIMS_TESTING_SHEET_TASK_ABSOLUTE_DATA")
@Builder(toBuilder = true)
@AllArgsConstructor
public class TestingSheetTaskAbsoluteData extends AutoKeyEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5725511325050185394L;
	
	private TestingSheetTaskAbsolute absolute;
	
	private TestingSheetTaskResult sheetTaskResult;

	private String quantifyInstrument; // 定量仪器

	private Double wkSize; // 文库量

	private String unitConversion; // 单位换算
	
	public TestingSheetTaskAbsoluteData(){
		
	}
	
	@ManyToOne(cascade= CascadeType.ALL,fetch= FetchType.LAZY)
    @JoinColumn(name="ABSOLUTE_ID")
	@JsonIgnore
	public TestingSheetTaskAbsolute getAbsolute() {
		return absolute;
	}

	public void setAbsolute(TestingSheetTaskAbsolute absolute) {
		this.absolute = absolute;
	}
	
	@ManyToOne(cascade= CascadeType.ALL,fetch= FetchType.LAZY)
    @JoinColumn(name="SHEET_TASK_RESULT_ID")
	@JsonIgnore
	public TestingSheetTaskResult getSheetTaskResult() {
		return sheetTaskResult;
	}

	public void setSheetTaskResult(TestingSheetTaskResult sheetTaskResult) {
		this.sheetTaskResult = sheetTaskResult;
	}

	@Column(name = "QUANTIFY_INSTRUMENT")
	public String getQuantifyInstrument() {
		return quantifyInstrument;
	}

	public void setQuantifyInstrument(String quantifyInstrument) {
		this.quantifyInstrument = quantifyInstrument;
	}

	@Column(name = "KW_SIZE")
	public Double getWkSize() {
		return wkSize;
	}

	public void setWkSize(Double wkSize) {
		this.wkSize = wkSize;
	}

	@Column(name = "UNIT_CONVERSION")
	public String getUnitConversion() {
		return unitConversion;
	}

	public void setUnitConversion(String unitConversion) {
		this.unitConversion = unitConversion;
	}
}
