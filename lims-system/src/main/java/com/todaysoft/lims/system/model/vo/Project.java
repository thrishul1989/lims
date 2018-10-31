package com.todaysoft.lims.system.model.vo;

import java.util.List;




import com.todaysoft.lims.system.model.vo.enums.ProjectStatus;
import com.todaysoft.lims.system.modules.smm.model.UserDetailsModel;

public class Project {

	private Integer id;
	private String code;
	private String name;
	private String projectType;
	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	private UserDetailsModel projectLeader; // 项目负责人
	private Integer projectLeaderId;
	private UserDetailsModel technicalLeader; // 技术负责人
	private Integer technicalLeaderId;
	private UserDetailsModel experimentLeader; // 实验负责人
	private Integer experimentLeaderId;
	private Customer customer; // 客户
	private Integer customerId;
	private String contractNo; // 合同号
	private Double expenditure; // 经费
	private ProjectStatus status; // 状态
	private String accessory; // 附件
	private List<Product> productList; // 检测项目
	private String productArray;
	private String describe; // 描述
	
	
	
	private Integer pageSize;
	private Integer pageNo;

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UserDetailsModel getProjectLeader() {
		return projectLeader;
	}

	public void setProjectLeader(UserDetailsModel projectLeader) {
		this.projectLeader = projectLeader;
	}

	public Integer getProjectLeaderId() {
		return projectLeaderId;
	}

	public void setProjectLeaderId(Integer projectLeaderId) {
		this.projectLeaderId = projectLeaderId;
	}

	public UserDetailsModel getTechnicalLeader() {
		return technicalLeader;
	}

	public void setTechnicalLeader(UserDetailsModel technicalLeader) {
		this.technicalLeader = technicalLeader;
	}

	public Integer getTechnicalLeaderId() {
		return technicalLeaderId;
	}

	public void setTechnicalLeaderId(Integer technicalLeaderId) {
		this.technicalLeaderId = technicalLeaderId;
	}

	public UserDetailsModel getExperimentLeader() {
		return experimentLeader;
	}

	public void setExperimentLeader(UserDetailsModel experimentLeader) {
		this.experimentLeader = experimentLeader;
	}

	public Integer getExperimentLeaderId() {
		return experimentLeaderId;
	}

	public void setExperimentLeaderId(Integer experimentLeaderId) {
		this.experimentLeaderId = experimentLeaderId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public Double getExpenditure() {
		return expenditure;
	}

	public void setExpenditure(Double expenditure) {
		this.expenditure = expenditure;
	}

	public ProjectStatus getStatus() {
		return status;
	}

	public void setStatus(ProjectStatus status) {
		this.status = status;
	}

	public String getAccessory() {
		return accessory;
	}

	public void setAccessory(String accessory) {
		this.accessory = accessory;
	}

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

	public String getProductArray() {
		return productArray;
	}

	public void setProductArray(String productArray) {
		this.productArray = productArray;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}


}
