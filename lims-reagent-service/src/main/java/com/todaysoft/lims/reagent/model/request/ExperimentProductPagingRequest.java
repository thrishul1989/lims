package com.todaysoft.lims.reagent.model.request;


public class ExperimentProductPagingRequest {
	private int pageNo;
	private int pageSize;
	private String code; // 产物编号
	private String name; // 产物名称
	private String containType; // 容器类型
	private String unit;//单位
	private Integer id;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
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
