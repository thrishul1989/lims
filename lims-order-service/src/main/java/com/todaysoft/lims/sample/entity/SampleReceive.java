package com.todaysoft.lims.sample.entity;

import com.todaysoft.lims.persist.AutoKeyEntity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "LIMS_SAMPLE_RECEIVE")
public class SampleReceive extends AutoKeyEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String receiveCode;//接受编号
    
    private String description;//描述
    
    private Date acceptDate;//接受日期
    
    private String acceptPerson;//接收人
    
    private String receiveType;//接收类型
    
    private String relatedItems;//关联项目 
    
    private String orderId;//关联订单
    
   
	@Column(name = "RECEIVE_CODE")
	public String getReceiveCode() {
		return receiveCode;
	}

	public void setReceiveCode(String receiveCode) {
		this.receiveCode = receiveCode;
	}
	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	@Column(name = "ACCEPT_DATE")
	public Date getAcceptDate() {
		return acceptDate;
	}

	public void setAcceptDate(Date acceptDate) {
		this.acceptDate = acceptDate;
	}
	
	@Column(name = "ACCEPT_PERSON")
	public String getAcceptPerson() {
		return acceptPerson;
	}

	public void setAcceptPerson(String acceptPerson) {
		this.acceptPerson = acceptPerson;
	}
	  @Column(name = "RECEIVE_TYPE")
	public String getReceiveType() {
		return receiveType;
	}

	public void setReceiveType(String receiveType) {
		this.receiveType = receiveType;
	}
	  @Column(name = "RELATED_ITEMS")
	public String getRelatedItems() {
		return relatedItems;
	}

	public void setRelatedItems(String relatedItems) {
		this.relatedItems = relatedItems;
	}
	  @Column(name = "ORDERID")
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
    

}
