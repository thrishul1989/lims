package com.todaysoft.lims.sample.entity;

import com.todaysoft.lims.persist.AutoKeyEntity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "LIMS_ORDER_EXPERIMENT_PRODUCT")
public class OrderExperimentProduct extends AutoKeyEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5591729314251348673L;

	private Integer orderId;
	private Integer sampleDetailId; //样本明细
	private Integer taskId;
	private Integer expProductId; //实验产物id
	private String locationCode;
	private Double storeAmount; //存储量
	private String unit; //单位
	private Date storeTime; //存储时间

	@Column(name = "ORDER_ID")
	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	
	@Column(name = "SAMPLE_DETAIL_ID")
	public Integer getSampleDetailId() {
		return sampleDetailId;
	}

	public void setSampleDetailId(Integer sampleDetailId) {
		this.sampleDetailId = sampleDetailId;
	}

	@Column(name = "TASK_ID")
	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	@Column(name = "EXP_PRODUCT_ID")
	public Integer getExpProductId() {
		return expProductId;
	}

	public void setExpProductId(Integer expProductId) {
		this.expProductId = expProductId;
	}

	@Column(name = "LOCATION_CODE")
	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	@Column(name = "STORE_AMOUNT")
	public Double getStoreAmount() {
		return storeAmount;
	}

	public void setStoreAmount(Double storeAmount) {
		this.storeAmount = storeAmount;
	}

	@Column(name = "UNIT")
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Column(name = "STORE_TIME")
	public Date getStoreTime() {
		return storeTime;
	}

	public void setStoreTime(Date storeTime) {
		this.storeTime = storeTime;
	}
}
