package com.todaysoft.lims.sample.model;

import java.util.Date;

/**
 * Created by HSHY-032 on 2016/10/11.
 */
public class SampleModel {
	private Date createTime; //下达任务创建日期
	private Integer id;
	private Integer receiveId;
	private String code;//下达页面code
	private String sampleInstanceCode;// 接受样本编号
	private String name; // 样本类型
	private String inspectMan;// 受检者姓名
	private String temporaryStorageLocation;// 临时存放位置
	private String inputId;// 输入id
	private String outputSampleLocation;// 样本存放位置
	private String indexCode;// 接头编号
	private String probeName;// 探针
	private String Method;// 检测方法
	private String testCode;// 质检编号
	private String sourceSampleLocation;// 接收DNA储位
	private String sourceSampleCode;// 接收样本编号
	private String sourceSampleType;// 样本类型
	private String testedName;// 实验编号
	private String targetSampleCode;// 文库编号
	private String targetSampleLocation;// 接收DNA储位
	private String activitiTaskId;// 工作流
	private String serialCode;// 编号
	private String concentration;// 捕获浓度
	private String diluteVolume;// 稀释加水体积
	private String finalConcentration;// 终浓度（ng/ul）
	private String dataSize;// 数据量
	private String sampleSize;// 样本投入量（ul）
	private String detectionMethod;// 检测方法
	private String fragmentLength;// 片段长度
	private String concentrationPC;// 上机浓度
	private String cluster;// cluster
	private String wkSizeList;// 文库量
	private String unitConversionList;// 单位换算
	private String nM;// Concentration(nM)
	private String projectName;// 项目编号
	private String receiveType;// 检测分类
	private String fileName;// 文件名称
	private String personName;// 姓名
	private String sex;// 性别
	private String caseNum;// 病历号
	private String productName;// 检测项目
	private String sendDate;// 送检日期
	private String age;// 年龄
	private String institution;// 送检单位
	private String checkManagement;// 检测方法
	private String reportDate;// 报告日期
	private String clinicalDiagnosis;// 临床诊断
	private String focusGenes;// 重点关注基因
	private String caseSummary;// 病史摘要
	private String familyHistory;// 是否有家族史
	private String familyHistorySummary;// 家族史摘要
	private String sampleName;// 样本形式
	private String customerName;// 客户姓名
	private String businessLeader;// 业务负责人
	private String technicalLeader;// 技术负责人
	private String reportLeader;// 报告负责人
	private String reportCompleteDate;// 报告完成日期
	private String reportVerifier;// 报告审核人
	private String remark;// 备注
	
	
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
	public String getSampleInstanceCode() {
		return sampleInstanceCode;
	}
	public void setSampleInstanceCode(String sampleInstanceCode) {
		this.sampleInstanceCode = sampleInstanceCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInspectMan() {
		return inspectMan;
	}
	public void setInspectMan(String inspectMan) {
		this.inspectMan = inspectMan;
	}
	public String getTemporaryStorageLocation() {
		return temporaryStorageLocation;
	}
	public void setTemporaryStorageLocation(String temporaryStorageLocation) {
		this.temporaryStorageLocation = temporaryStorageLocation;
	}
	public String getInputId() {
		return inputId;
	}
	public void setInputId(String inputId) {
		this.inputId = inputId;
	}
	public String getOutputSampleLocation() {
		return outputSampleLocation;
	}
	public void setOutputSampleLocation(String outputSampleLocation) {
		this.outputSampleLocation = outputSampleLocation;
	}
	public String getIndexCode() {
		return indexCode;
	}
	public void setIndexCode(String indexCode) {
		this.indexCode = indexCode;
	}
	public String getProbeName() {
		return probeName;
	}
	public void setProbeName(String probeName) {
		this.probeName = probeName;
	}
	public String getMethod() {
		return Method;
	}
	public void setMethod(String method) {
		Method = method;
	}
	public String getTestCode() {
		return testCode;
	}
	public void setTestCode(String testCode) {
		this.testCode = testCode;
	}
	public String getSourceSampleLocation() {
		return sourceSampleLocation;
	}
	public void setSourceSampleLocation(String sourceSampleLocation) {
		this.sourceSampleLocation = sourceSampleLocation;
	}
	public String getSourceSampleCode() {
		return sourceSampleCode;
	}
	public void setSourceSampleCode(String sourceSampleCode) {
		this.sourceSampleCode = sourceSampleCode;
	}
	public String getSourceSampleType() {
		return sourceSampleType;
	}
	public void setSourceSampleType(String sourceSampleType) {
		this.sourceSampleType = sourceSampleType;
	}
	public String getTestedName() {
		return testedName;
	}
	public void setTestedName(String testedName) {
		this.testedName = testedName;
	}
	public String getTargetSampleCode() {
		return targetSampleCode;
	}
	public void setTargetSampleCode(String targetSampleCode) {
		this.targetSampleCode = targetSampleCode;
	}
	public String getTargetSampleLocation() {
		return targetSampleLocation;
	}
	public void setTargetSampleLocation(String targetSampleLocation) {
		this.targetSampleLocation = targetSampleLocation;
	}
	public String getActivitiTaskId() {
		return activitiTaskId;
	}
	public void setActivitiTaskId(String activitiTaskId) {
		this.activitiTaskId = activitiTaskId;
	}
	public String getSerialCode() {
		return serialCode;
	}
	public void setSerialCode(String serialCode) {
		this.serialCode = serialCode;
	}
	public String getConcentration() {
		return concentration;
	}
	public void setConcentration(String concentration) {
		this.concentration = concentration;
	}
	public String getDiluteVolume() {
		return diluteVolume;
	}
	public void setDiluteVolume(String diluteVolume) {
		this.diluteVolume = diluteVolume;
	}
	public String getFinalConcentration() {
		return finalConcentration;
	}
	public void setFinalConcentration(String finalConcentration) {
		this.finalConcentration = finalConcentration;
	}
	public String getDataSize() {
		return dataSize;
	}
	public void setDataSize(String dataSize) {
		this.dataSize = dataSize;
	}
	public String getSampleSize() {
		return sampleSize;
	}
	public void setSampleSize(String sampleSize) {
		this.sampleSize = sampleSize;
	}
	public String getDetectionMethod() {
		return detectionMethod;
	}
	public void setDetectionMethod(String detectionMethod) {
		this.detectionMethod = detectionMethod;
	}
	public String getFragmentLength() {
		return fragmentLength;
	}
	public void setFragmentLength(String fragmentLength) {
		this.fragmentLength = fragmentLength;
	}
	public String getConcentrationPC() {
		return concentrationPC;
	}
	public void setConcentrationPC(String concentrationPC) {
		this.concentrationPC = concentrationPC;
	}
	public String getCluster() {
		return cluster;
	}
	public void setCluster(String cluster) {
		this.cluster = cluster;
	}
	public String getWkSizeList() {
		return wkSizeList;
	}
	public void setWkSizeList(String wkSizeList) {
		this.wkSizeList = wkSizeList;
	}
	public String getUnitConversionList() {
		return unitConversionList;
	}
	public void setUnitConversionList(String unitConversionList) {
		this.unitConversionList = unitConversionList;
	}
	public String getnM() {
		return nM;
	}
	public void setnM(String nM) {
		this.nM = nM;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getReceiveType() {
		return receiveType;
	}
	public void setReceiveType(String receiveType) {
		this.receiveType = receiveType;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getCaseNum() {
		return caseNum;
	}
	public void setCaseNum(String caseNum) {
		this.caseNum = caseNum;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getSendDate() {
		return sendDate;
	}
	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getInstitution() {
		return institution;
	}
	public void setInstitution(String institution) {
		this.institution = institution;
	}
	public String getCheckManagement() {
		return checkManagement;
	}
	public void setCheckManagement(String checkManagement) {
		this.checkManagement = checkManagement;
	}
	public String getReportDate() {
		return reportDate;
	}
	public void setReportDate(String reportDate) {
		this.reportDate = reportDate;
	}
	public String getClinicalDiagnosis() {
		return clinicalDiagnosis;
	}
	public void setClinicalDiagnosis(String clinicalDiagnosis) {
		this.clinicalDiagnosis = clinicalDiagnosis;
	}
	public String getFocusGenes() {
		return focusGenes;
	}
	public void setFocusGenes(String focusGenes) {
		this.focusGenes = focusGenes;
	}
	public String getCaseSummary() {
		return caseSummary;
	}
	public void setCaseSummary(String caseSummary) {
		this.caseSummary = caseSummary;
	}
	public String getFamilyHistory() {
		return familyHistory;
	}
	public void setFamilyHistory(String familyHistory) {
		this.familyHistory = familyHistory;
	}
	public String getFamilyHistorySummary() {
		return familyHistorySummary;
	}
	public void setFamilyHistorySummary(String familyHistorySummary) {
		this.familyHistorySummary = familyHistorySummary;
	}
	public String getSampleName() {
		return sampleName;
	}
	public void setSampleName(String sampleName) {
		this.sampleName = sampleName;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getBusinessLeader() {
		return businessLeader;
	}
	public void setBusinessLeader(String businessLeader) {
		this.businessLeader = businessLeader;
	}
	public String getTechnicalLeader() {
		return technicalLeader;
	}
	public void setTechnicalLeader(String technicalLeader) {
		this.technicalLeader = technicalLeader;
	}
	public String getReportLeader() {
		return reportLeader;
	}
	public void setReportLeader(String reportLeader) {
		this.reportLeader = reportLeader;
	}
	public String getReportCompleteDate() {
		return reportCompleteDate;
	}
	public void setReportCompleteDate(String reportCompleteDate) {
		this.reportCompleteDate = reportCompleteDate;
	}
	public String getReportVerifier() {
		return reportVerifier;
	}
	public void setReportVerifier(String reportVerifier) {
		this.reportVerifier = reportVerifier;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getReceiveId() {
		return receiveId;
	}
	public void setReceiveId(Integer receiveId) {
		this.receiveId = receiveId;
	}
}
