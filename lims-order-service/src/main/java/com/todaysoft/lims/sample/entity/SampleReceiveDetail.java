package com.todaysoft.lims.sample.entity;

import com.todaysoft.lims.persist.AutoKeyEntity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

//样品接收明细
@Entity
@Table(name = "LIMS_SAMPLE_DETAIL")
public class SampleReceiveDetail extends AutoKeyEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String code;//编号
	private String name; //样本名称
	private String sampleType;//样本类型
	private int sampleCount;//样本数量
	private String company;//单位
	private String familyName;//家系姓名
	private String sampleIdentification;//样本标识
	private String sampleRelationship;//样本关系 
	private String samplePurpose;//样本用途
	private String isQualified;//是否合格
	private String reason;//备注原因
	private String temporaryStorageLocation;//临检存储位置
	private String longtermStorageLocation;//长期存储位置
	private String inspectMan;//受检人
	
	private String state; //状态： 未启动、启动
	private String sampleInstanceCode;  //样本实例code
	
	private Integer receiveId;  //样本接收 父节点ID
	
	private String productId; //查询产品信息
	
	private String productName;  //查询产品名称
	
	private String temporaryStorageAmount; //临时存取量
	
	private String longtermStorageAmount; //长期存取量
	
	private Date createTime;
	
	private List<String> productList;
	
	private String isSave;  //0 未保存  1已保存
	
	private String acceptDate;
	
	private String createUser; //创建者
	private String creatorId;
	
	
	
	
	@Transient
	public String getAcceptDate() {
		return acceptDate;
	}

	public void setAcceptDate(String acceptDate) {
		this.acceptDate = acceptDate;
	}

	@Column(name="IS_SAVE")
	public String getIsSave() {
		return isSave;
	}

	public void setIsSave(String isSave) {
		this.isSave = isSave;
	}

	@Transient
	public List<String> getProductList() {
		return productList;
	}

	public void setProductList(List<String> productList) {
		this.productList = productList;
	}

	@Column(name="RECEIVE_ID")
	public Integer getReceiveId() {
		return receiveId;
	}

	public void setReceiveId(Integer receiveId) {
		this.receiveId = receiveId;
	}


	@Column(name="SAMPLE_INSTANCE_CODE")
	public String getSampleInstanceCode() {
		return sampleInstanceCode;
	}

	public void setSampleInstanceCode(String sampleInstanceCode) {
		this.sampleInstanceCode = sampleInstanceCode;
	}

	@Column(name="INSPECT_MAN")
	public String getInspectMan() {
		return inspectMan;
	}
	
	public void setInspectMan(String inspectMan) {
		this.inspectMan = inspectMan;
	}
	
	
	@Column(name="NAME")
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	@Column(name="FAMILY_NAME")
	public String getFamilyName() {
		return familyName;
	}
	
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}
	@Column(name="REASON")
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	@Column(name="SAMPLE_RELATIONSHIP")
	public String getSampleRelationship() {
		return sampleRelationship;
	}
	public void setSampleRelationship(String sampleRelationship) {
		this.sampleRelationship = sampleRelationship;
	}
	@Column(name="CODE")
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@Column(name="COMPANY")
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	@Column(name="SAMPLE_COUNT")
	public int getSampleCount() {
		return sampleCount;
	}
	public void setSampleCount(int sampleCount) {
		this.sampleCount = sampleCount;
	}
	@Column(name="SAMPLE_TYPE")
	public String getSampleType() {
		return sampleType;
	}

	public void setSampleType(String sampleType) {
		this.sampleType = sampleType;
	}
	@Column(name="SAMPLE_IDENTIFICATION")
	public String getSampleIdentification() {
		return sampleIdentification;
	}

	public void setSampleIdentification(String sampleIdentification) {
		this.sampleIdentification = sampleIdentification;
	}
	@Column(name="SAMPLE_PURPOSE")
	public String getSamplePurpose() {
		return samplePurpose;
	}

	public void setSamplePurpose(String samplePurpose) {
		this.samplePurpose = samplePurpose;
	}
	@Column(name="IS_QUALIFIED")
	public String getIsQualified() {
		return isQualified;
	}

	public void setIsQualified(String isQualified) {
		this.isQualified = isQualified;
	}
	@Column(name="TEMPORARY_STORAGE_LOCATION")
	public String getTemporaryStorageLocation() {
		return temporaryStorageLocation;
	}
	public void setTemporaryStorageLocation(String temporaryStorageLocation) {
		this.temporaryStorageLocation = temporaryStorageLocation;
	}
	@Column(name="LONGTERM_STORAGE_LOCATION")
	public String getLongtermStorageLocation() {
		return longtermStorageLocation;
	}
	public void setLongtermStorageLocation(String longtermStorageLocation) {
		this.longtermStorageLocation = longtermStorageLocation;
	}

	@Column(name="STATE")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	
	@Column(name="ITEMPORARY_STORAGE_AMOUNT")
	public String getTemporaryStorageAmount() {
		return temporaryStorageAmount;
	}

	public void setTemporaryStorageAmount(String temporaryStorageAmount) {
		this.temporaryStorageAmount = temporaryStorageAmount;
	}

	@Column(name="LONGTERM_STORAGE_AMOUNT")
	public String getLongtermStorageAmount() {
		return longtermStorageAmount;
	}

	public void setLongtermStorageAmount(String longtermStorageAmount) {
		this.longtermStorageAmount = longtermStorageAmount;
	}

	@Column(name="CREATE_TIME")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Transient
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	@Transient
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	@Column(name="CREATE_USER")
	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	@Column(name="CREATOR_ID")
	public String getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}
}
