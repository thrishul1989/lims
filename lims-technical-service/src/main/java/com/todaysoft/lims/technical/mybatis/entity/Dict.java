package com.todaysoft.lims.technical.mybatis.entity;

import java.util.List;



public class Dict {
	private String id;

	private String parentId;

	private String category;

	private String dictText;

	private String dictValue;

	private Integer sort;

	private Boolean editable;

	private List<Dict> entries;

	public List<Dict> getEntries() {
		return entries;
	}

	public void setEntries(List<Dict> entries) {
		this.entries = entries;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDictText() {
		return dictText;
	}

	public void setDictText(String dictText) {
		this.dictText = dictText;
	}

	public String getDictValue() {
		return dictValue;
	}

	public void setDictValue(String dictValue) {
		this.dictValue = dictValue;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Boolean getEditable() {
		return editable;
	}

	public void setEditable(Boolean editable) {
		this.editable = editable;
	}
}