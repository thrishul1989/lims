package com.todaysoft.lims.sample.model.request;

import java.util.Date;

public class OrderSampleCreateRequest {
	private Integer orderId;
	private Integer sampleDetailId;
	private String sampleType;
	private Integer sampleKey;
	private String locationCode;
	private Integer source;
	private Double usageAmount;
	private String unit;
	private Date storeTime;

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

	public String getSampleType() {
		return sampleType;
	}

	public void setSampleType(String sampleType) {
		this.sampleType = sampleType;
	}

	public Integer getSampleKey() {
		return sampleKey;
	}

	public void setSampleKey(Integer sampleKey) {
		this.sampleKey = sampleKey;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public Double getUsageAmount() {
		return usageAmount;
	}

	public void setUsageAmount(Double usageAmount) {
		this.usageAmount = usageAmount;
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
