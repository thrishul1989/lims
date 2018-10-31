package com.todaysoft.lims.gateway.model.request.samplereceive;

//样品接收明细
public class SampleReceiveDetailRequest {

	private Integer id;// 序号
	private String code;// 编号
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

	private String createUser;
	private String creatorId;

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getInspectMan() {
		return inspectMan;
	}

	public void setInspectMan(String inspectMan) {
		this.inspectMan = inspectMan;
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

	public SampleReceiveDetailRequest() {
	}

	public SampleReceiveDetailRequest(Integer id, String sampleType,
			int sampleCount, String sampleIdentification, String samplePurpose,
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
