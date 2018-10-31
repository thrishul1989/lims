package com.todaysoft.lims.system.model.vo;



public class ExperimentProduct {
	private String id;
	
	private int pageNo;
	private int pageSize;
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
	private String code; // 产物编号
	private String name; // 产物名称
	private String containType; // 存储容器
	private String unit;//单位
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
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
	public String getContainType() {
		return containType;
	}
	public void setContainType(String containType) {
		this.containType = containType;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
