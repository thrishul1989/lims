package com.todaysoft.lims.sample.entity;

import com.todaysoft.lims.persist.AutoKeyEntity;

import java.util.Date;


public class TestingTaskDetail extends AutoKeyEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7549732932548300506L;

	private String jointNum; //接头编号
	private String testCode; //实验编号
	private String testedName; //受检人姓名
	private String sourceSampleCode; //源样本编号
	private String sourceSampleType; //源样本类型
	private String sourceSampleLocation; //源样本位置
	private String targetSampleCode; // 目标样本编号
	private String targetSampleLocation; //目标样本位置
	private String testTaskId; //下达任务主键
	private double volume;//体积
	private String activitiTaskId;
	private Integer probeId; //探针主键
	private String probeName; //探针名称
	private String connectorId; //接头编号（用于文库构建时）
	private Double concentration;//浓度
	private Double diluteVolume; //稀释加水体积
	private Double finalConcentration; //最终浓度
	private Double dataSize; //数据量
	private Double sampleSize;//样本投入量
	private String detectionMethod; //检测方法
	private Date createTime;//上机时间
	private Double fourHtOne;
	private Double oneEightHtOne;
	private Double cluster;
	
	private String wkbhCode;
	private String libraryCode;
	private String coordinate; // 分析坐标
	private String productName;
	private String sex;
	private String receiveType;
	private String institution; //送检单位
	private Integer sampleDetailId;
	private Integer productId;
	
	private String projectName;
	private String fileName;
	private String caseNum;//病例号
	private Date sendDate;
	private Integer age;
	private String checkManagement;
	private Date reportDate;
	private String probes;
	private String clinicalDiagnosis;//临床诊断
	private String focusGenes;//重点关注基因
	private String caseSummary;//病例摘要
	private String familyHistory;//是否有家族史
	private String familyHistorySummary;//家族史摘要
	private String customerName;
	private String businessLeader;//业务负责人
	private String technicalLeader;// 技术负责人
	private String reportLeader;//报告负责人
	private Date reportCompleteDate;//报告完成日期
	private String reportVerifier;//报告审核人
	private String remark;
    private String serialCode;//编号
    
    private String testingTaskId;
    private Date sampleReceiveDate;
    private String products;
    private Integer taskState;
    private Double inputAmount;
    
	public String getSerialCode() {
		return serialCode;
	}

	public void setSerialCode(String serialCode) {
		this.serialCode = serialCode;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Double getFourHtOne() {
		return fourHtOne;
	}

	public void setFourHtOne(Double fourHtOne) {
		this.fourHtOne = fourHtOne;
	}

	public Double getOneEightHtOne() {
		return oneEightHtOne;
	}

	public void setOneEightHtOne(Double oneEightHtOne) {
		this.oneEightHtOne = oneEightHtOne;
	}

	public Double getCluster() {
		return cluster;
	}

	public void setCluster(Double cluster) {
		this.cluster = cluster;
	}

	public Integer getProbeId() {
		return probeId;
	}

	public void setProbeId(Integer probeId) {
		this.probeId = probeId;
	}

	public String getConnectorId() {
		return connectorId;
	}

	public void setConnectorId(String connectorId) {
		this.connectorId = connectorId;
	}

	public String getProbeName() {
		return probeName;
	}

	public void setProbeName(String probeName) {
		this.probeName = probeName;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public Double getConcentration() {
		return concentration;
	}

	public void setConcentration(Double concentration) {
		this.concentration = concentration;
	}

	public String getJointNum() {
		return jointNum;
	}

	public void setJointNum(String jointNum) {
		this.jointNum = jointNum;
	}

	public String getActivitiTaskId() {
		return activitiTaskId;
	}

	public void setActivitiTaskId(String activitiTaskId) {
		this.activitiTaskId = activitiTaskId;
	}

	private Sample sample;

	public String getTestCode() {
		return testCode;
	}

	public void setTestCode(String testCode) {
		this.testCode = testCode;
	}

	public String getTestedName() {
		return testedName;
	}

	public void setTestedName(String testedName) {
		this.testedName = testedName;
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

	public String getSourceSampleLocation() {
		return sourceSampleLocation;
	}

	public void setSourceSampleLocation(String sourceSampleLocation) {
		this.sourceSampleLocation = sourceSampleLocation;
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

	public String getTestTaskId() {
		return testTaskId;
	}

	public void setTestTaskId(String testTaskId) {
		this.testTaskId = testTaskId;
	}

	public Sample getSample() {
		return sample;
	}

	public void setSample(Sample sample) {
		this.sample = sample;
	}

	public Double getDiluteVolume() {
		return diluteVolume;
	}

	public void setDiluteVolume(Double diluteVolume) {
		this.diluteVolume = diluteVolume;
	}

	public Double getFinalConcentration() {
		return finalConcentration;
	}

	public void setFinalConcentration(Double finalConcentration) {
		this.finalConcentration = finalConcentration;
	}

	public Double getDataSize() {
		return dataSize;
	}

	public void setDataSize(Double dataSize) {
		this.dataSize = dataSize;
	}

	public Double getSampleSize() {
		return sampleSize;
	}

	public void setSampleSize(Double sampleSize) {
		this.sampleSize = sampleSize;
	}

	public String getDetectionMethod() {
		return detectionMethod;
	}

	public void setDetectionMethod(String detectionMethod) {
		this.detectionMethod = detectionMethod;
	}

	public String getWkbhCode() {
		return wkbhCode;
	}

	public void setWkbhCode(String wkbhCode) {
		this.wkbhCode = wkbhCode;
	}

	public String getLibraryCode() {
		return libraryCode;
	}

	public void setLibraryCode(String libraryCode) {
		this.libraryCode = libraryCode;
	}

	public String getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getReceiveType() {
		return receiveType;
	}

	public void setReceiveType(String receiveType) {
		this.receiveType = receiveType;
	}

	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}

	public Integer getSampleDetailId() {
		return sampleDetailId;
	}

	public void setSampleDetailId(Integer sampleDetailId) {
		this.sampleDetailId = sampleDetailId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getCaseNum() {
		return caseNum;
	}

	public void setCaseNum(String caseNum) {
		this.caseNum = caseNum;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getCheckManagement() {
		return checkManagement;
	}

	public void setCheckManagement(String checkManagement) {
		this.checkManagement = checkManagement;
	}

	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	public String getProbes() {
		return probes;
	}

	public void setProbes(String probes) {
		this.probes = probes;
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

	public Date getReportCompleteDate() {
		return reportCompleteDate;
	}

	public void setReportCompleteDate(Date reportCompleteDate) {
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

	public String getTestingTaskId() {
		return testingTaskId;
	}

	public void setTestingTaskId(String testingTaskId) {
		this.testingTaskId = testingTaskId;
	}

	public Date getSampleReceiveDate() {
		return sampleReceiveDate;
	}

	public void setSampleReceiveDate(Date sampleReceiveDate) {
		this.sampleReceiveDate = sampleReceiveDate;
	}

	public String getProducts() {
		return products;
	}

	public void setProducts(String products) {
		this.products = products;
	}

	public Integer getTaskState() {
		return taskState;
	}

	public void setTaskState(Integer taskState) {
		this.taskState = taskState;
	}

	public Double getInputAmount() {
		return inputAmount;
	}

	public void setInputAmount(Double inputAmount) {
		this.inputAmount = inputAmount;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "TestingTaskDetail [testCode=" + testCode + ", testedName="
				+ testedName + ", sourceSampleCode=" + sourceSampleCode
				+ ", sourceSampleType=" + sourceSampleType
				+ ", sourceSampleLocation=" + sourceSampleLocation
				+ ", targetSampleCode=" + targetSampleCode
				+ ", targetSampleLocation=" + targetSampleLocation
				+ ", testTaskId=" + testTaskId + ", volume=" + volume
				+ ", activitiTaskId=" + activitiTaskId + ", probeId=" + probeId
				+ ", probeName=" + probeName + ", connectorId=" + connectorId
				+ ", concentration=" + concentration + ", sample=" + sample
				+ "]";
	}

	
	

	
}
