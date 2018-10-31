package com.todaysoft.lims.sample.model;

public class Probe {
	private Integer id;
	private String code;
	private String name;
	private Double dataSize; // 数据量
	private String unit;
	private Double qualitySize; // 质量
	private String qualityUnit; // 质量单位
	private Integer status; // 状态
	private String remark;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Double getDataSize() {
		return dataSize;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public void setDataSize(Double dataSize) {
		this.dataSize = dataSize;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Double getQualitySize() {
		return qualitySize;
	}

	public void setQualitySize(Double qualitySize) {
		this.qualitySize = qualitySize;
	}

	public String getQualityUnit() {
		return qualityUnit;
	}

	public void setQualityUnit(String qualityUnit) {
		this.qualityUnit = qualityUnit;
	}
}
