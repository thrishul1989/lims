package com.todaysoft.lims.gateway.model.request.order;

import java.util.Date;

public class OrderSampleView {
	
	private String orderId;
	
	private String sampleType;
	
	private String sampleCode;
	
	private int sampleSize;

	private String sampleName;
	
	private Date samplingDate;
	
	private String sampleId;
	
	private Integer sampleBtype;
	
	private Integer purpose;
	
	

	public Integer getPurpose() {
		return purpose;
	}

	public void setPurpose(Integer purpose) {
		this.purpose = purpose;
	}

	public String getSampleId() {
		return sampleId;
	}

	public void setSampleId(String sampleId) {
		this.sampleId = sampleId;
	}

	public Integer getSampleBtype() {
		return sampleBtype;
	}

	public void setSampleBtype(Integer sampleBtype) {
		this.sampleBtype = sampleBtype;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getSampleType() {
		return sampleType;
	}

	public void setSampleType(String sampleType) {
		this.sampleType = sampleType;
	}

	public String getSampleCode() {
		return sampleCode;
	}

	public void setSampleCode(String sampleCode) {
		this.sampleCode = sampleCode;
	}

	public int getSampleSize() {
		return sampleSize;
	}

	public void setSampleSize(int sampleSize) {
		this.sampleSize = sampleSize;
	}

	public String getSampleName() {
		return sampleName;
	}

	public void setSampleName(String sampleName) {
		this.sampleName = sampleName;
	}

	public Date getSamplingDate() {
		return samplingDate;
	}

	public void setSamplingDate(Date samplingDate) {
		this.samplingDate = samplingDate;
	}
	
	
	
	
}
