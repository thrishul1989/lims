package com.todaysoft.lims.reagent.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.todaysoft.lims.persist.UuidKeyEntity;


@Entity
@Table(name = "LIMS_PRODUCTER_MANAGE")
public class ProducterManage extends UuidKeyEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
	
	@Transient
	public String getEmailVal() {
		return emailVal;
	}

	public void setEmailVal(String emailVal) {
		this.emailVal = emailVal;
	}
	
	@Transient
	public DataArea getDataArea() {
		return dataArea;
	}

	public void setDataArea(DataArea dataArea) {
		this.dataArea = dataArea;
	}
	
	@Column(name = "PRODUCTER_NO")
	public String getProducterNo() {
		return producterNo;
	}

	public void setProducterNo(String producterNo) {
		this.producterNo = producterNo;
	}
	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "STATE")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	@Column(name = "PHONE")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Column(name = "EMAIL")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	@Column(name = "ADDRESS")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	@Column(name = "WEBSITE")
	public String getWebsite() {
		return website;
	}
	
	public void setWebsite(String website) {
		this.website = website;
	}

	@Column(name = "CONTACT_NAME")
	public String getContactName() {
		return contactName;
	}
	
	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	@Column(name = "CONTACT_PHONE")
	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	@Column(name = "CONTACT_EMAILS")
	public String getContactEmails() {
		return contactEmails;
	}

	public void setContactEmails(String contactEmails) {
		this.contactEmails = contactEmails;
	}
	@Column(name = "PROVINCE")
	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	@Column(name = "CITY")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@Column(name = "AREA")
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
}
