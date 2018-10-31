package com.todaysoft.lims.gateway.model.request;


public class ExperimentProductCreateRequest {
	private String code; // 产物编号
	private String name; // 产物名称
	private String containType; // 容器类型
	private String unit;//单位
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
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

	public String getContainType() {
		return containType;
	}

	public void setContainType(String containType) {
		this.containType = containType;
	}
}
