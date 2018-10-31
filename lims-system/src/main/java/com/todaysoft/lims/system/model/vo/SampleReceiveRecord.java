package com.todaysoft.lims.system.model.vo;

import java.util.Date;

//样品接收
public class SampleReceiveRecord {
	private Integer id;
	
    private String receiveCode;//接受编号
    
    private String description;//描述
    
    private Date acceptDate;//接受日期
    
    private String acceptPerson;//接收人
    
    private String receiveType;//接收类型
    
    private String relatedItems;//关联项目 ？科研  ？临床
    
    private Project project;
    
    private String orderId;
    
    private String detailId; 
    
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

	
    public SampleReceiveRecord(){}
    
    public SampleReceiveRecord(Integer id,String receiveCode, String state, String description,
			Date acceptDate, String itemId, String acceptPerson) {
		super();
		this.id = id;
		this.receiveCode = receiveCode;
		this.description = description;
		this.acceptDate = acceptDate;
		this.acceptPerson = acceptPerson;
	}

    
    
   
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getDetailId() {
		return detailId;
	}

	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getReceiveCode() {
		return receiveCode;
	}

	public void setReceiveCode(String receiveCode) {
		this.receiveCode = receiveCode;
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

	
	public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }

	public String getAcceptPerson() {
		return acceptPerson;
	}

	public void setAcceptPerson(String acceptPerson) {
		this.acceptPerson = acceptPerson;
	}

	public Date getAcceptDate() {
		return acceptDate;
	}

	public void setAcceptDate(Date acceptDate) {
		this.acceptDate = acceptDate;
	}

    
}
