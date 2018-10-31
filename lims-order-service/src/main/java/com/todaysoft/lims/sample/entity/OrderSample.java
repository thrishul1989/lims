package com.todaysoft.lims.sample.entity;

import com.todaysoft.lims.persist.AutoKeyEntity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "LIMS_ORDER_SAMPLE")
public class OrderSample extends AutoKeyEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1004974061942028504L;

	private Integer orderId; //
	private Integer sampleDetailId; //样本明细
	private String sampleType; // 样本类型，0-样本，1-产物
	private Integer sampleKey; // 样本实例的id，如果是样本就保存样本id，如果是实验产物，就保存实验产物id
	private String locationCode; // 存储位置编号
	private Integer source; // 样本来源，0-样本接收，其他为具体任务id
	private Double usageAmount; //使用量
	private String unit; //单位
	private Date storeTime; // 样本存储时间

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

	@Column(name = "SAMPLE_TYPE")
	public String getSampleType() {
		return sampleType;
	}

	public void setSampleType(String sampleType) {
		this.sampleType = sampleType;
	}

	@Column(name = "SAMPLE_KEY")
	public Integer getSampleKey() {
		return sampleKey;
	}

	public void setSampleKey(Integer sampleKey) {
		this.sampleKey = sampleKey;
	}

	@Column(name = "LOCATION_CODE")
	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	@Column(name = "SOURCE")
	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	@Column(name = "USAGE_AMOUNT")
	public Double getUsageAmount() {
		return usageAmount;
	}

	public void setUsageAmount(Double usageAmount) {
		this.usageAmount = usageAmount;
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
