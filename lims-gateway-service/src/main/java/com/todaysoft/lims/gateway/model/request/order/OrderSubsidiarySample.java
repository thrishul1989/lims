package com.todaysoft.lims.gateway.model.request.order;

import com.todaysoft.lims.persist.UuidKeyEntity;

import java.util.Date;


/**
 * 订单-附属样本
 * @author admin
 *
 */

public class OrderSubsidiarySample extends UuidKeyEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String sampleCode; //样本编号
	private String sampleTypeId;//样本类型ID
	private int sampleSize;//样本量
	private String samplingDate;//采样时间
	
	private String familyRelation;
	private String ownerName;
	private int ownerAge;
	private int ownerPhenotype;
	private int purpose;
	
	private OrderExaminee orderExaminee; //订单受检人
	
	private Order order; //订单对象
	
	public OrderSubsidiarySample() {
		super();
	}
	
	public String getFamilyRelation() {
		return familyRelation;
	}

	public void setFamilyRelation(String familyRelation) {
		this.familyRelation = familyRelation;
	}
	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public int getOwnerAge() {
		return ownerAge;
	}

	public void setOwnerAge(int ownerAge) {
		this.ownerAge = ownerAge;
	}
	public int getOwnerPhenotype() {
		return ownerPhenotype;
	}

	public void setOwnerPhenotype(int ownerPhenotype) {
		this.ownerPhenotype = ownerPhenotype;
	}
	public int getPurpose() {
		return purpose;
	}

	public void setPurpose(int purpose) {
		this.purpose = purpose;
	}

	
	public String getSampleCode() {
		return sampleCode;
	}

	public void setSampleCode(String sampleCode) {
		this.sampleCode = sampleCode;
	}
	public String getSampleTypeId() {
		return sampleTypeId;
	}

	public void setSampleTypeId(String sampleTypeId) {
		this.sampleTypeId = sampleTypeId;
	}
	
	public int getSampleSize() {
		return sampleSize;
	}


	public void setSampleSize(int sampleSize) {
		this.sampleSize = sampleSize;
	}
	public String getSamplingDate() {
		return samplingDate;
	}

	public void setSamplingDate(String samplingDate) {
		this.samplingDate = samplingDate;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
	public OrderExaminee getOrderExaminee() {
		return orderExaminee;
	}

	public void setOrderExaminee(OrderExaminee orderExaminee) {
		this.orderExaminee = orderExaminee;
	}
	
	
	
	
	
	
	
}
