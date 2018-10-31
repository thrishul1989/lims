package com.todaysoft.lims.gateway.model;

import java.util.Date;

public class OrderExperimentProduct {
	private Integer id;
	private Integer orderId;
	private Integer sampleDetailId;
	private Integer taskId;
	private Integer expProductId; // 实验产物id
	private String locationCode;
	private Double storeAmount; // 存储量
	private String unit; //单位
	private Date storeTime; // 存储时间

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getSampleDetailId() {
		return sampleDetailId;
	}

	public void setSampleDetailId(Integer sampleDetailId) {
		this.sampleDetailId = sampleDetailId;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public Integer getExpProductId() {
		return expProductId;
	}

	public void setExpProductId(Integer expProductId) {
		this.expProductId = expProductId;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public Double getStoreAmount() {
		return storeAmount;
	}

	public void setStoreAmount(Double storeAmount) {
		this.storeAmount = storeAmount;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Date getStoreTime() {
		return storeTime;
	}

	public void setStoreTime(Date storeTime) {
		this.storeTime = storeTime;
	}
}
