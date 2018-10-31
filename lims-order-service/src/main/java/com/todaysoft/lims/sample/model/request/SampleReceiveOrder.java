package com.todaysoft.lims.sample.model.request;

import java.util.Date;
//订单
public class SampleReceiveOrder {
	
	private Integer id;
	
	
	/*****************受检人基本信息******************/
	
	private String subjectPNum;//受检人编号
	
	private String subjectPname;//受检人
	
	private String sex;//性别
	
	private String nation;//民族
	
	private String phone;//联系电话
	
	private Date birthday;//出生日期
	
	private String contacts;//联系人
	
	private String address;//地址
	
	
	/*****************订单基本信息******************/
	
	private String orderCode; //订单编号
	
	private String testProduct;//检测产品  ---关联产品表
	
	private String focusGenes;//重点关注基因
	
	private String loveLetter;//知情书
	
	private String sirf;//样本信息登记单

	private String caseNum;//病例号
	
	private String inpatientNum;//住院号

	private String outpatientNum;//门诊号 
	
	private String clinicalDiagnosis;//临床诊断
	
	private String caseSummary;//病例摘要

	private String remarks;//备注

	private String orderName;
	
	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getSubjectPNum() {
		return subjectPNum;
	}

	public void setSubjectPNum(String subjectPNum) {
		this.subjectPNum = subjectPNum;
	}

	public String getSubjectPname() {
		return subjectPname;
	}

	public void setSubjectPname(String subjectPname) {
		this.subjectPname = subjectPname;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getTestProduct() {
		return testProduct;
	}

	public void setTestProduct(String testProduct) {
		this.testProduct = testProduct;
	}

	public String getFocusGenes() {
		return focusGenes;
	}

	public void setFocusGenes(String focusGenes) {
		this.focusGenes = focusGenes;
	}

	public String getLoveLetter() {
		return loveLetter;
	}

	public void setLoveLetter(String loveLetter) {
		this.loveLetter = loveLetter;
	}

	public String getSirf() {
		return sirf;
	}

	public void setSirf(String sirf) {
		this.sirf = sirf;
	}

	public String getCaseNum() {
		return caseNum;
	}

	public void setCaseNum(String caseNum) {
		this.caseNum = caseNum;
	}

	public String getInpatientNum() {
		return inpatientNum;
	}

	public void setInpatientNum(String inpatientNum) {
		this.inpatientNum = inpatientNum;
	}

	public String getOutpatientNum() {
		return outpatientNum;
	}

	public void setOutpatientNum(String outpatientNum) {
		this.outpatientNum = outpatientNum;
	}

	public String getClinicalDiagnosis() {
		return clinicalDiagnosis;
	}

	public void setClinicalDiagnosis(String clinicalDiagnosis) {
		this.clinicalDiagnosis = clinicalDiagnosis;
	}

	public String getCaseSummary() {
		return caseSummary;
	}

	public void setCaseSummary(String caseSummary) {
		this.caseSummary = caseSummary;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	

}
