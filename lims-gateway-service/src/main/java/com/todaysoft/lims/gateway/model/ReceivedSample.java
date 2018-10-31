package com.todaysoft.lims.gateway.model;

import java.io.Serializable;
import java.util.Date;




public class ReceivedSample implements Serializable{

	 public ReceivedSample() {
		super();
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 private String  sampleId;// '主键',
	 private String  orderId; //'冗余字段-订单ID',
	 private Integer businessType; //业务类型：1-非科研主样本 2-非科研附属样本 3-科研样本
	 private String  typeId; //'冗余字段-类型ID',
	 private String  typeName ;//'冗余字段-类型名称',
	 private String  sampleCode;//冗余字段-样本编号',
	 private String  sampleName;//'冗余字段-样本名称',
	 private Date  samplingDate;//'冗余字段-采样时间',
	 private String  lsLocation;// '长期存储位置编号',
	 private double  lsSize;//'长期存储量',
	 private Integer lsStatus;//'长期存储状态：1-入库 2-出库',
	 private String  tsLocation;//'临时存储位置编号',
	 private double  tsSize;//'临时存储量',
	 private Integer tsStatus;// '临时存储状态：1-入库 2-出库',
	 
	 private String parentId; //返回一个已保存id
	 
	 
	 
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getSampleId() {
		return sampleId;
	}
	public void setSampleId(String sampleId) {
		this.sampleId = sampleId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Integer getBusinessType() {
		return businessType;
	}
	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
	}
	
	public String getTypeId() {
		return typeId;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getSampleCode() {
		return sampleCode;
	}
	public void setSampleCode(String sampleCode) {
		this.sampleCode = sampleCode;
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
	public String getLsLocation() {
		return lsLocation;
	}
	public void setLsLocation(String lsLocation) {
		this.lsLocation = lsLocation;
	}
	public double getLsSize() {
		return lsSize;
	}
	public void setLsSize(double lsSize) {
		this.lsSize = lsSize;
	}
	public Integer getLsStatus() {
		return lsStatus;
	}
	public void setLsStatus(Integer lsStatus) {
		this.lsStatus = lsStatus;
	}
	public String getTsLocation() {
		return tsLocation;
	}
	public void setTsLocation(String tsLocation) {
		this.tsLocation = tsLocation;
	}
	public double getTsSize() {
		return tsSize;
	}
	public void setTsSize(double tsSize) {
		this.tsSize = tsSize;
	}
	public Integer getTsStatus() {
		return tsStatus;
	}
	public void setTsStatus(Integer tsStatus) {
		this.tsStatus = tsStatus;
	}
	

}
