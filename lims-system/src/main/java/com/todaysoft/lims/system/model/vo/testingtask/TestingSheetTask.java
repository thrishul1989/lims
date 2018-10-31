package com.todaysoft.lims.system.model.vo.testingtask;

import java.util.Date;
import java.util.List;

import com.todaysoft.lims.system.model.vo.SampleReceiveDetail;
import com.todaysoft.lims.system.model.vo.TestingSheet;
import com.todaysoft.lims.system.model.vo.TestingSheetTaskCatch;

public class TestingSheetTask {

	private Integer id;

	private String sheetId;

	private TestingSheet testingSheet;

	private Integer previousSheetTaskId;

	private Integer activitiTaskId;

	private String sampleType;

	private String testingCode;

	private String inputSampleInstanceId;

	private SampleReceiveDetail sampleDetail;

	private String inputSamplecode;

	private String inputSampleLocation;

	private String outputSamplecode;

	private String outputSampleLocation;

	private TestingSheetTaskResult taskResult;

	private String connectorId;

	private String probeId; // 探针code

	private String probeName;

	private Double concentration;// 文库捕获浓度

	private List<TestingSheetTaskCatch> sheetTaskCatchList; // 任务单子表（文库捕获）

	private Double diluteVolume; // 稀释加水体积

	private Double finalConcentration; // 最终浓度

	private Double dataSize; // 数据量

	private Double sampleSize;// 样本投入量

	private String detectionMethod; // 检测方法 相对定量检测方法 用来区分是否是PCR+NGS方法

	private Date createTime;// 上机时间

	private Double fourHtOne;// dilute to 4nM Add HT1
	
	private Double fourHtOneValue;

	private Double oneEightHtOne;// dilute to 1.8pM (Volume=1500ul)
	
	private Double oneEightHtOneValue;

	private Integer cluster;
	
	private Integer absoluteId;

	private String SomeMan;

	private String sampleTypeName;

	private String originalCode;
	private String libraryCode;
	private String coordinate; // 分析坐标
	private String productName;
	private String sex;
	private String receiveType;
	private String institution; // 送检单位
	private String wkbhCode;
	private String testedName;
	private Integer sampleDetailId;
	private Integer productId;
	
	private String projectName;
	private String fileName;
	private String caseNum;//病例号
	private Date sendDate;//下达时间 
	private String inspectName;//受检人
	private Integer age;
	private String checkManagement;
	private Date reportDate;
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
    private String  sendName;//任务下达人
    private String testingName;//实验负责人
	
	private String method;//检测方法
	
	private String serialCode;//自动生成编号
	
	private String inputType;
	private Integer inputId;
    
	private String testingTaskId;
	private String products;
	private Double InputQuantity;//默认输入量
	private Double outputQuantity;//默认输出量
	private String notes;
	
	private String index;
	
	private String sourceSampleType;
	
	private String sourceSampleCode;
	
	private String targetSampleCode;
	
	
    
	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getSourceSampleType() {
		return sourceSampleType;
	}

	public void setSourceSampleType(String sourceSampleType) {
		this.sourceSampleType = sourceSampleType;
	}

	public String getSourceSampleCode() {
		return sourceSampleCode;
	}

	public void setSourceSampleCode(String sourceSampleCode) {
		this.sourceSampleCode = sourceSampleCode;
	}

	public String getTargetSampleCode() {
		return targetSampleCode;
	}

	public void setTargetSampleCode(String targetSampleCode) {
		this.targetSampleCode = targetSampleCode;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getSerialCode() {
		return serialCode;
	}

	public void setSerialCode(String serialCode) {
		this.serialCode = serialCode;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getSendName() {
		return sendName;
	}

	public void setSendName(String sendName) {
		this.sendName = sendName;
	}

	public String getTestingName() {
		return testingName;
	}

	public void setTestingName(String testingName) {
		this.testingName = testingName;
	}

	public String getTestedName() {
		return testedName;
	}

	public void setTestedName(String testedName) {
		this.testedName = testedName;
	}

	public String getWkbhCode() {
		return wkbhCode;
	}

	public void setWkbhCode(String wkbhCode) {
		this.wkbhCode = wkbhCode;
	}

	public String getSomeMan() {
		return SomeMan;
	}

	public void setSomeMan(String someMan) {
		SomeMan = someMan;
	}

	public String getSampleTypeName() {
		return sampleTypeName;
	}

	public void setSampleTypeName(String sampleTypeName) {
		this.sampleTypeName = sampleTypeName;
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

	public Integer getCluster() {
		return cluster;
	}

	public void setCluster(Integer cluster) {
		this.cluster = cluster;
	}

	public List<TestingSheetTaskCatch> getSheetTaskCatchList() {
		return sheetTaskCatchList;
	}

	public void setSheetTaskCatchList(
			List<TestingSheetTaskCatch> sheetTaskCatchList) {
		this.sheetTaskCatchList = sheetTaskCatchList;
	}

	public String getConnectorId() {
		return connectorId;
	}

	public String getProbeId() {
		return probeId;
	}

	public void setProbeId(String probeId) {
		this.probeId = probeId;
	}

	public void setConnectorId(String connectorId) {
		this.connectorId = connectorId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSheetId() {
		return sheetId;
	}

	public void setSheetId(String sheetId) {
		this.sheetId = sheetId;
	}

	public TestingSheet getTestingSheet() {
		return testingSheet;
	}

	public void setTestingSheet(TestingSheet testingSheet) {
		this.testingSheet = testingSheet;
	}

	public Integer getPreviousSheetTaskId() {
		return previousSheetTaskId;
	}

	public void setPreviousSheetTaskId(Integer previousSheetTaskId) {
		this.previousSheetTaskId = previousSheetTaskId;
	}

	public Integer getActivitiTaskId() {
		return activitiTaskId;
	}

	public void setActivitiTaskId(Integer activitiTaskId) {
		this.activitiTaskId = activitiTaskId;
	}

	public String getSampleType() {
		return sampleType;
	}

	public void setSampleType(String sampleType) {
		this.sampleType = sampleType;
	}

	public String getTestingCode() {
		return testingCode;
	}

	public void setTestingCode(String testingCode) {
		this.testingCode = testingCode;
	}

	public String getInputSampleInstanceId() {
		return inputSampleInstanceId;
	}

	public void setInputSampleInstanceId(String inputSampleInstanceId) {
		this.inputSampleInstanceId = inputSampleInstanceId;
	}

	public SampleReceiveDetail getSampleDetail() {
		return sampleDetail;
	}

	public void setSampleDetail(SampleReceiveDetail sampleDetail) {
		this.sampleDetail = sampleDetail;
	}

	public String getInputSamplecode() {
		return inputSamplecode;
	}

	public void setInputSamplecode(String inputSamplecode) {
		this.inputSamplecode = inputSamplecode;
	}

	public String getInputSampleLocation() {
		return inputSampleLocation;
	}

	public void setInputSampleLocation(String inputSampleLocation) {
		this.inputSampleLocation = inputSampleLocation;
	}

	public String getOutputSamplecode() {
		return outputSamplecode;
	}

	public void setOutputSamplecode(String outputSamplecode) {
		this.outputSamplecode = outputSamplecode;
	}

	public String getOutputSampleLocation() {
		return outputSampleLocation;
	}

	public void setOutputSampleLocation(String outputSampleLocation) {
		this.outputSampleLocation = outputSampleLocation;
	}

	public TestingSheetTaskResult getTaskResult() {
		return taskResult;
	}

	public void setTaskResult(TestingSheetTaskResult taskResult) {
		this.taskResult = taskResult;
	}

	public String getProbeName() {
		return probeName;
	}

	public void setProbeName(String probeName) {
		this.probeName = probeName;
	}

	public Double getConcentration() {
		return concentration;
	}

	public void setConcentration(Double concentration) {
		this.concentration = concentration;
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

	public String getOriginalCode() {
		return originalCode;
	}

	public void setOriginalCode(String originalCode) {
		this.originalCode = originalCode;
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

	public String getInspectName() {
		return inspectName;
	}

	public void setInspectName(String inspectName) {
		this.inspectName = inspectName;
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

	public Double getFourHtOneValue() {
		return fourHtOneValue;
	}

	public void setFourHtOneValue(Double fourHtOneValue) {
		this.fourHtOneValue = fourHtOneValue;
	}

	public Double getOneEightHtOneValue() {
		return oneEightHtOneValue;
	}

	public void setOneEightHtOneValue(Double oneEightHtOneValue) {
		this.oneEightHtOneValue = oneEightHtOneValue;
	}

	public Integer getAbsoluteId() {
		return absoluteId;
	}

	public void setAbsoluteId(Integer absoluteId) {
		this.absoluteId = absoluteId;
	}

	public String getInputType() {
		return inputType;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	public Integer getInputId() {
		return inputId;
	}

	public void setInputId(Integer inputId) {
		this.inputId = inputId;
	}

	public String getTestingTaskId() {
		return testingTaskId;
	}

	public void setTestingTaskId(String testingTaskId) {
		this.testingTaskId = testingTaskId;
	}

	public String getProducts() {
		return products;
	}

	public void setProducts(String products) {
		this.products = products;
	}

	public Double getInputQuantity() {
		return InputQuantity;
	}

	public void setInputQuantity(Double inputQuantity) {
		InputQuantity = inputQuantity;
	}

	public Double getOutputQuantity() {
		return outputQuantity;
	}

	public void setOutputQuantity(Double outputQuantity) {
		this.outputQuantity = outputQuantity;
	}
}
