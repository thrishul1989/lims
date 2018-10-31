package com.todaysoft.lims.technical.mybatis.entity;

import java.util.Date;

public class CompareSample {
	private String sampleCode;
	private String orderCode;
	private String productName;
	private String productCode;
	private String dataCode;
	private String methodName;
	private String sampleName;
	private String sequecingCode;
	private Date createTime;
	private String bamFile;
	
	public String getSampleCode() {
		return sampleCode;
	}
	public void setSampleCode(String sampleCode) {
		this.sampleCode = sampleCode;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getDataCode() {
		return dataCode;
	}
	public void setDataCode(String dataCode) {
		this.dataCode = dataCode;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getSampleName() {
		return sampleName;
	}
	public void setSampleName(String sampleName) {
		this.sampleName = sampleName;
	}
	public String getSequecingCode() {
		return sequecingCode;
	}
	public void setSequecingCode(String sequecingCode) {
		this.sequecingCode = sequecingCode;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getBamFile() {
		return bamFile;
	}
	public void setBamFile(String bamFile) {
		this.bamFile = bamFile;
	}
}
