package com.todaysoft.lims.sample.entity;

import com.todaysoft.lims.persist.AutoKeyEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "LIMS_PROJECT")
public class Project extends AutoKeyEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 26517482096605345L;

	private String code;
	private String name;
	private Integer projectLeaderId; // 项目负责人
	private Integer technicalLeaderId; // 技术负责人
	private Integer experimentLeaderId; // 实验负责人
	private Integer customerId; // 客户
	private String contractNo; // 合同号
	private Double expenditure; // 经费
	private ProjectStatus status; // 状态 
	private String accessory; // 附件
	private String productArray; // 检测项目
	private String describe; // 描述
	private String projectType; //项目类型

	@Column(name = "CODE")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "PROJECT_LEADER")
	public Integer getProjectLeaderId() {
		return projectLeaderId;
	}

	public void setProjectLeaderId(Integer projectLeaderId) {
		this.projectLeaderId = projectLeaderId;
	}

	@Column(name = "TECHNICAL_LEADER")
	public Integer getTechnicalLeaderId() {
		return technicalLeaderId;
	}

	public void setTechnicalLeaderId(Integer technicalLeaderId) {
		this.technicalLeaderId = technicalLeaderId;
	}

	@Column(name = "EXPERIMENT_LEADER")
	public Integer getExperimentLeaderId() {
		return experimentLeaderId;
	}

	public void setExperimentLeaderId(Integer experimentLeaderId) {
		this.experimentLeaderId = experimentLeaderId;
	}

	@Column(name = "CUSTOMER_ID")
	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	@Column(name = "CONTRACT_NO")
	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	@Column(name = "EXPENDITURE")
	public Double getExpenditure() {
		return expenditure;
	}

	public void setExpenditure(Double expenditure) {
		this.expenditure = expenditure;
	}

	@Column(name = "STATUS")
	public ProjectStatus getStatus() {
		return status;
	}

	public void setStatus(ProjectStatus status) {
		this.status = status;
	}

	@Column(name = "ACCESSORY")
	public String getAccessory() {
		return accessory;
	}

	public void setAccessory(String accessory) {
		this.accessory = accessory;
	}

	@Column(name = "PRODUCT_LIST")
	public String getProductArray() {
		return productArray;
	}

	public void setProductArray(String productArray) {
		this.productArray = productArray;
	}

	@Column(name = "DESCRIBING")
	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	@Column(name = "PROJECT_TYPE")
	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}
	
}
