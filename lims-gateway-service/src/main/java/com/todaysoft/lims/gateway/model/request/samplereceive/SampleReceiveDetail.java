package com.todaysoft.lims.gateway.model.request.samplereceive;

import java.util.List;

//样品接收明细
public class SampleReceiveDetail {

	private Integer id;// 序号
	private String code;// 编号
	private String name;// 样本名
	private String sampleType;// 样本类型
	private int sampleCount;// 样本数量
	private String company;// 单位
	private String familyName;// 家系姓名
	private String sampleIdentification;// 样本标识
	private String sampleRelationship;// 样本关系
	private String samplePurpose;// 样本用途
	private String isQualified;// 是否合格
	private String reason;// 备注原因
	private String temporaryStorageLocation;// 临检存储位置
	private String longtermStorageLocation;// 长期存储位置
	private String inspectMan;// 受检人

	private String state; // 状态： 未启动、启动
	private String sampleInstanceCode; // 样本实例code

	private String productId;
	private List<String> productList;

	private String acceptDate;

	private String createUser;
	private String creatorId;

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getAcceptDate() {
		return acceptDate;
	}

	public void setAcceptDate(String acceptDate) {
		this.acceptDate = acceptDate;
	}

	public List<String> getProductList() {
		return productList;
	}

	public void setProductList(List<String> productList) {
		this.productList = productList;
	}

	private String temporaryStorageAmount;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	private String longtermStorageAmount;

	public String getTemporaryStorageAmount() {
		return temporaryStorageAmount;
	}

	public void setTemporaryStorageAmount(String temporaryStorageAmount) {
		this.temporaryStorageAmount = temporaryStorageAmount;
	}

	public String getLongtermStorageAmount() {
		return longtermStorageAmount;
	}

	public void setLongtermStorageAmount(String longtermStorageAmount) {
		this.longtermStorageAmount = longtermStorageAmount;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getSampleInstanceCode() {
		return sampleInstanceCode;
	}

	public void setSampleInstanceCode(String sampleInstanceCode) {
		this.sampleInstanceCode = sampleInstanceCode;
	}

	public String getInspectMan() {
		return inspectMan;
	}

	public void setInspectMan(String inspectMan) {
		this.inspectMan = inspectMan;
	}

	public SampleReceiveDetail() {
	}

	public SampleReceiveDetail(Integer id, String sampleType, int sampleCount,
			String sampleIdentification, String samplePurpose,
			String isQualified, String temporaryStorageLocation,
			String longtermStorageLocation) {
		super();
		this.id = id;
		this.sampleType = sampleType;
		this.sampleCount = sampleCount;
		this.sampleIdentification = sampleIdentification;
		this.samplePurpose = samplePurpose;
		this.isQualified = isQualified;
		this.temporaryStorageLocation = temporaryStorageLocation;
		this.longtermStorageLocation = longtermStorageLocation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getSampleRelationship() {
		return sampleRelationship;
	}

	public void setSampleRelationship(String sampleRelationship) {
		this.sampleRelationship = sampleRelationship;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public int getSampleCount() {
		return sampleCount;
	}

	public void setSampleCount(int sampleCount) {
		this.sampleCount = sampleCount;
	}

	public String getSampleType() {
		return sampleType;
	}

	public void setSampleType(String sampleType) {
		this.sampleType = sampleType;
	}

	public String getSampleIdentification() {
		return sampleIdentification;
	}

	public void setSampleIdentification(String sampleIdentification) {
		this.sampleIdentification = sampleIdentification;
	}

	public String getSamplePurpose() {
		return samplePurpose;
	}

	public void setSamplePurpose(String samplePurpose) {
		this.samplePurpose = samplePurpose;
	}

	public String getIsQualified() {
		return isQualified;
	}

	public void setIsQualified(String isQualified) {
		this.isQualified = isQualified;
	}

	public String getTemporaryStorageLocation() {
		return temporaryStorageLocation;
	}

	public void setTemporaryStorageLocation(String temporaryStorageLocation) {
		this.temporaryStorageLocation = temporaryStorageLocation;
	}

	public String getLongtermStorageLocation() {
		return longtermStorageLocation;
	}

	public void setLongtermStorageLocation(String longtermStorageLocation) {
		this.longtermStorageLocation = longtermStorageLocation;
	}

	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

}
