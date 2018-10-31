package com.todaysoft.lims.gateway.model;


public class ProducterManage {
	
	private String id; 

	private String producterNo; //生产商编号
	
	private String name;//名称
	
	private String state;//状态
	
	private String phone;//电话
	
	private String email;//邮箱
	
	private String address;//地址
	
	private String website;//网址
	
	private String description;//描述
	
	private String contactName;//联系人
	
	private String contactPhone;//联系人电话
	
	private String contactEmails;//联系人邮箱
	
	private String province;
	
	private String city;
	
	private DataArea dataArea;
	
	private String emailVal;
	
	private String area;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
	public String getEmailVal() {
		return emailVal;
	}

	public void setEmailVal(String emailVal) {
		this.emailVal = emailVal;
	}
	
	public DataArea getDataArea() {
		return dataArea;
	}

	public void setDataArea(DataArea dataArea) {
		this.dataArea = dataArea;
	}
	
	public String getProducterNo() {
		return producterNo;
	}

	public void setProducterNo(String producterNo) {
		this.producterNo = producterNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getContactEmails() {
		return contactEmails;
	}

	public void setContactEmails(String contactEmails) {
		this.contactEmails = contactEmails;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

}
