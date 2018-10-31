package com.todaysoft.lims.system.modules.bpm.tecanalys.model;

import java.util.List;


public class ClaimTemplateColumnForDoctor {

	private String type;
	private String semantic;
	private String columnName;
	private List<FilterItems> chooseFilterItemsList;
	private List<FilterItems> chooseDefaultValueList;
	private String rangeValue1;
	private String rangeValue2;
	private String radioValue;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSemantic() {
		return semantic;
	}

	public void setSemantic(String semantic) {
		this.semantic = semantic;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public List<FilterItems> getChooseFilterItemsList() {
		return chooseFilterItemsList;
	}

	public void setChooseFilterItemsList(List<FilterItems> chooseFilterItemsList) {
		this.chooseFilterItemsList = chooseFilterItemsList;
	}

	public List<FilterItems> getChooseDefaultValueList() {
		return chooseDefaultValueList;
	}

	public void setChooseDefaultValueList(List<FilterItems> chooseDefaultValueList) {
		this.chooseDefaultValueList = chooseDefaultValueList;
	}

	public String getRangeValue1() {
		return rangeValue1;
	}

	public void setRangeValue1(String rangeValue1) {
		this.rangeValue1 = rangeValue1;
	}

	public String getRangeValue2() {
		return rangeValue2;
	}

	public void setRangeValue2(String rangeValue2) {
		this.rangeValue2 = rangeValue2;
	}

	public String getRadioValue() {
		return radioValue;
	}

	public void setRadioValue(String radioValue) {
		this.radioValue = radioValue;
	}
}
