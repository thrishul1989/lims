package com.todaysoft.lims.gateway.model;

import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.gateway.model.product.ProductProbe;




public class Product {
	private String id;
	private String code; // 产品编号
	private String name; // 产品名称
	private Double price; // 产品定价￥
	private Integer productType; // 产品类型
	private Integer testingType;// 检测类型
	private Integer testingDuration; // 产品周期 天
	private String description;// 产品描述
	private String probeArray;// 探针ids
	private List<Probe> probeList = new ArrayList<Probe>();
	private List<ProductTask> productTaskList = new ArrayList<ProductTask>();
	private String experimentSamples;//实验样本
	private User creater;// 创建人
	private String createTime;// 创建时间
	private String enabled;// 状态
	private String diseaseCategory;// 疾病类型
	private Integer testingGenes;// 基因数
	private String remark;// 产品备注
	private List<ProductSample> productSampleList;
	private List<ProductTestingMethod> productTestingMethodList;
	private List<ProductPrincipal> productPrincipalList;
	private List<ProductDisease> productDiseaseList;
	private List<ProductGene> productGeneList;
	
	public String getExperimentSamples() {
		return experimentSamples;
	}
	public void setExperimentSamples(String experimentSamples) {
		this.experimentSamples = experimentSamples;
	}
	public List<ProductGene> getProductGeneList() {
		return productGeneList;
	}
	public void setProductGeneList(List<ProductGene> productGeneList) {
		this.productGeneList = productGeneList;
	}
	public List<ProductDisease> getProductDiseaseList() {
		return productDiseaseList;
	}
	public void setProductDiseaseList(List<ProductDisease> productDiseaseList) {
		this.productDiseaseList = productDiseaseList;
	}
	public Integer getTestingType() {
		return testingType;
	}
	public void setTestingType(Integer testingType) {
		this.testingType = testingType;
	}
	public User getCreater() {
		return creater;
	}
	public void setCreater(User creater) {
		this.creater = creater;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public List<ProductSample> getProductSampleList() {
		return productSampleList;
	}
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	public void setProductSampleList(List<ProductSample> productSampleList) {
		this.productSampleList = productSampleList;
	}
	public List<ProductTestingMethod> getProductTestingMethodList() {
		return productTestingMethodList;
	}
	public void setProductTestingMethodList(List<ProductTestingMethod> productTestingMethodList) {
		this.productTestingMethodList = productTestingMethodList;
	}

	
	public String getId() {
		return id;
	}
	public List<ProductPrincipal> getProductPrincipalList() {
		return productPrincipalList;
	}
	public void setProductPrincipalList(List<ProductPrincipal> productPrincipalList) {
		this.productPrincipalList = productPrincipalList;
	}
	public void setId(String id) {
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
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	
	
	public Integer getTestingDuration() {
		return testingDuration;
	}
	public void setTestingDuration(Integer testingDuration) {
		this.testingDuration = testingDuration;
	}
	public Integer getProductType() {
		return productType;
	}
	public void setProductType(Integer productType) {
		this.productType = productType;
	}
	public String getDiseaseCategory() {
		return diseaseCategory;
	}
	public void setDiseaseCategory(String diseaseCategory) {
		this.diseaseCategory = diseaseCategory;
	}
	public Integer getTestingGenes() {
		return testingGenes;
	}
	public void setTestingGenes(Integer testingGenes) {
		this.testingGenes = testingGenes;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<Probe> getProbeList() {
		return probeList;
	}
	public void setProbeList(List<Probe> probeList) {
		this.probeList = probeList;
	}
	public String getProbeArray() {
		return probeArray;
	}
	public void setProbeArray(String probeArray) {
		this.probeArray = probeArray;
	}
	public List<ProductTask> getProductTaskList() {
		return productTaskList;
	}
	public void setProductTaskList(List<ProductTask> productTaskList) {
		this.productTaskList = productTaskList;
	}
}
