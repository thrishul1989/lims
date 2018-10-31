package com.todaysoft.lims.system.model.vo;

public class Check {
	
	private Integer id;
	
	private String code;//检测编码
	
	private String name; // 检测名
	
	private String description;// 检测描述

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	
}
