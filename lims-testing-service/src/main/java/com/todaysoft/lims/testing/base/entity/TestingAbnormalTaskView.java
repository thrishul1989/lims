package com.todaysoft.lims.testing.base.entity;

import com.todaysoft.lims.persist.UuidKeyEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "LIMS_TESTING_ABNORMAL_TASK_VIEW")
public class TestingAbnormalTaskView extends UuidKeyEntity {

	private String name;

	private Date endTime;

	private String semantic;// 任务单类型标记

	private String remark;

	private String sampleName;

	private String sampleCode;

	private String orderCode;

	private String productName;

	private String orderType;

	private String methodName;

	private Integer resubmitCount;


	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "END_TIME")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name = "SEMANTIC")
	public String getSemantic() {
		return semantic;
	}

	public void setSemantic(String semantic) {
		this.semantic = semantic;
	}

	@Column(name = "REMARK")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "SAMPLE_NAME")
	public String getSampleName() {
		return sampleName;
	}

	public void setSampleName(String sampleName) {
		this.sampleName = sampleName;
	}

	@Column(name = "SAMPLE_CODE")
	public String getSampleCode() {
		return sampleCode;
	}

	public void setSampleCode(String sampleCode) {
		this.sampleCode = sampleCode;
	}

	@Column(name = "ORDER_CODE")
	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	@Column(name = "PRODUCT_NAME")
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@Column(name = "ORDER_TYPE")
	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	@Column(name = "METHOD_NAME")
	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	@Column(name = "RESUBMIT_COUNT")
	public Integer getResubmitCount() {
		return resubmitCount;
	}

	public void setResubmitCount(Integer resubmitCount) {
		this.resubmitCount = resubmitCount;
	}
}
