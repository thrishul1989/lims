package com.todaysoft.lims.sample.entity;

import com.todaysoft.lims.persist.AutoKeyEntity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "LIMS_SAMPLE_ORDER")
public class SampleOrder extends AutoKeyEntity {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
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
	
	private String testProduct;//检测产品   ---关联产品表
	
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
	
	private String productName;
	
	
	
	/****************补充信息******************/
	private  String chargeType;
	
	private  Double chargePrice;
	
	private  Date chargeTime;
	
	private String chargePerson;
	
	private String chargeState;
	
	private String payState;
	
	@Transient
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	@Column(name = "order_name")
	public String getOrderName() {
		return orderName;
	}
	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}
	public SampleOrder() {
		super();
	}
	
	
	@Column(name = "SUBJECT_P_NUM")
	public String getSubjectPNum() {
		return subjectPNum;
	}

	public void setSubjectPNum(String subjectPNum) {
		this.subjectPNum = subjectPNum;
	}
	 @Column(name = "SUBJECT_PNAME")
	public String getSubjectPname() {
		return subjectPname;
	}

	public void setSubjectPname(String subjectPname) {
		this.subjectPname = subjectPname;
	}
	 @Column(name = "SEX")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	 @Column(name = "NATION")
	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}
	 @Column(name = "PHONE")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	 @Column(name = "BIRTHDAY")
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	 @Column(name = "CONTACTS")
	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}
	 @Column(name = "ADDRESS")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	@Column(name = "ORDER_CODE")
	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	 @Column(name = "TEST_PRODUCT")
	public String getTestProduct() {
		return testProduct;
	}

	public void setTestProduct(String testProduct) {
		this.testProduct = testProduct;
	}
	 @Column(name = "FOCUS_GENES")
	public String getFocusGenes() {
		return focusGenes;
	}

	public void setFocusGenes(String focusGenes) {
		this.focusGenes = focusGenes;
	}
	 @Column(name = "LOVE_LETTER")
	public String getLoveLetter() {
		return loveLetter;
	}

	public void setLoveLetter(String loveLetter) {
		this.loveLetter = loveLetter;
	}
	 @Column(name = "SIRF")
	public String getSirf() {
		return sirf;
	}

	public void setSirf(String sirf) {
		this.sirf = sirf;
	}
	 @Column(name = "CASENUM")
	public String getCaseNum() {
		return caseNum;
	}

	public void setCaseNum(String caseNum) {
		this.caseNum = caseNum;
	}
	 @Column(name = "INPATIENT_NUM")
	public String getInpatientNum() {
		return inpatientNum;
	}

	public void setInpatientNum(String inpatientNum) {
		this.inpatientNum = inpatientNum;
	}
	 @Column(name = "OUTPATIENT_NUM")
	public String getOutpatientNum() {
		return outpatientNum;
	}

	public void setOutpatientNum(String outpatientNum) {
		this.outpatientNum = outpatientNum;
	}
	 @Column(name = "CLINICAL_DIAGNOSIS")
	public String getClinicalDiagnosis() {
		return clinicalDiagnosis;
	}

	public void setClinicalDiagnosis(String clinicalDiagnosis) {
		this.clinicalDiagnosis = clinicalDiagnosis;
	}
	 @Column(name = "CASE_SUMMARY")
	public String getCaseSummary() {
		return caseSummary;
	}

	public void setCaseSummary(String caseSummary) {
		this.caseSummary = caseSummary;
	}
	 @Column(name = "REMARKS")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	 @Column(name = "CHARGETYPE")
	public String getChargeType() {
		return chargeType;
	}
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}
	 @Column(name = "CHARGEPRICE")
	public Double getChargePrice() {
		return chargePrice;
	}
	public void setChargePrice(Double chargePrice) {
		this.chargePrice = chargePrice;
	}
	 @Column(name = "CHARGETIME")
	public Date getChargeTime() {
		return chargeTime;
	}
	public void setChargeTime(Date chargeTime) {
		this.chargeTime = chargeTime;
	}
	 @Column(name = "CHARGEPERSON")
	public String getChargePerson() {
		return chargePerson;
	}
	public void setChargePerson(String chargePerson) {
		this.chargePerson = chargePerson;
	}
	 @Column(name = "CHARGESTATE")
	public String getChargeState() {
		return chargeState;
	}
	public void setChargeState(String chargeState) {
		this.chargeState = chargeState;
	}
	 @Column(name = "PAYSTATE")
	public String getPayState() {
		return payState;
	}
	public void setPayState(String payState) {
		this.payState = payState;
	}

	
	
	
	
}
