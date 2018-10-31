package com.todaysoft.lims.gateway.model;

import java.util.Date;


public class SampleReceive {

	private Integer id;
	
	private String receiveCode;//接受编号
    
    private String description;//描述
    
    private Date acceptDate;//接受日期
    
    private String acceptPerson;//接收人
    
    private String receiveType;//接收类型
    
    private String relatedItems;//关联项目 ？科研  ？临床
    
    private String orderId;//关联订单
    
    private Project project;
    
    private String orderName;
    
    
	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getReceiveCode() {
		return receiveCode;
	}

	public void setReceiveCode(String receiveCode) {
		this.receiveCode = receiveCode;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	public Date getAcceptDate() {
		return acceptDate;
	}

	public void setAcceptDate(Date acceptDate) {
		this.acceptDate = acceptDate;
	}
	
	public String getAcceptPerson() {
		return acceptPerson;
	}

	public void setAcceptPerson(String acceptPerson) {
		this.acceptPerson = acceptPerson;
	}
	public String getReceiveType() {
		return receiveType;
	}

	public void setReceiveType(String receiveType) {
		this.receiveType = receiveType;
	}
	public String getRelatedItems() {
		return relatedItems;
	}

	public void setRelatedItems(String relatedItems) {
		this.relatedItems = relatedItems;
	}
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
    

}
