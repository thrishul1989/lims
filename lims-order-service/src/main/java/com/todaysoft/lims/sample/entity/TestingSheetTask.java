package com.todaysoft.lims.sample.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.todaysoft.lims.persist.AutoKeyEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "LIMS_TESTING_SHEET_TASK")
@Builder(toBuilder = true)
@AllArgsConstructor
public class TestingSheetTask extends AutoKeyEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -284106522414256160L;

	// private Integer sheetId;

	private TestingSheet testingSheet;

	private Integer previousSheetTaskId; // 上一步的任务id

	private String activitiTaskId; // 工作流任务id

	private String semantic; // 任务类型

	private String sampleType; // 原始样本名称

	private String testingCode; // 实验编号

	private String inputSampleInstanceId;

	// private SampleReceiveDetail sampleDetail;

	private String inputSamplecode;

	private String inputSampleLocation;

	private String outputSamplecode;

	private String outputSampleLocation;

	private Integer testingScheduleId; // scheduleId 用于跟踪流程

	private TestingSheetTaskResult taskResult;

	private String connectorId; // 接头编号（用于文库构建时）

	private String remark; // 备注

	private Integer sampleDetailId;

	private Integer productId;

	private Integer probeId;

	private String probeName;

	private Double concentration;// 文库捕获浓度

	private Double diluteVolume; // 稀释加水体积

	private Double finalConcentration; // 最终浓度

	private Double dataSize; // 数据量

	private Double sampleSize;// 样本投入量

	private List<TestingSheetTaskCatch> sheetTaskCatchList; // 任务单子表（文库捕获）

	private String detectionMethod; // 检测方法

	private Date createTime;// 上机时间

	private Double fourHtOne;// dilute to 4nM Add HT1
	
	private Double fourHtOneValue;

	private Double oneEightHtOne;// dilute to 1.8pM (Volume=1500ul)
	
	private Double oneEightHtOneValue;

	private Integer cluster;
	
	private Integer absoluteId;

	private String originalCode;
	private String libraryCode;
	private String coordinate; // 分析坐标
	private String productName;
	private String sex;
	private String receiveType;
	private String institution; // 送检单位

	private String projectName;
	private String fileName;
	private String caseNum;//病例号
	private Date sendDate;
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
	
	private String serialCode;//编号
	
	private String inputType;
	private Integer inputId;
	
	private String testingTaskId;
	private String products;
	private Double InputQuantity;//默认输入量
	private Double outputQuantity;//默认输出量
	private String notes;
	
	
	
	public TestingSheetTask() {

	}

	// @Column(name="sheet_id")
	// public Integer getSheetId() {
	// return sheetId;
	// }
	//
	// public void setSheetId(Integer sheetId) {
	// this.sheetId = sheetId;
	// }

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "sheet_id")
	@JsonIgnore
	public TestingSheet getTestingSheet() {
		return testingSheet;
	}

	public void setTestingSheet(TestingSheet testingSheet) {
		this.testingSheet = testingSheet;
	}

	@Column(name = "previous_sheet_task_id")
	public Integer getPreviousSheetTaskId() {
		return previousSheetTaskId;
	}

	public void setPreviousSheetTaskId(Integer previousSheetTaskId) {
		this.previousSheetTaskId = previousSheetTaskId;
	}

	@Column(name = "activiti_task_id")
	public String getActivitiTaskId() {
		return activitiTaskId;
	}

	public void setActivitiTaskId(String activitiTaskId) {
		this.activitiTaskId = activitiTaskId;
	}

	@Column(name = "semantic")
	public String getSemantic() {
		return semantic;
	}

	public void setSemantic(String semantic) {
		this.semantic = semantic;
	}

	@Column(name = "sample_type")
	public String getSampleType() {
		return sampleType;
	}

	public void setSampleType(String sampleType) {
		this.sampleType = sampleType;
	}

	@Column(name = "testing_code")
	public String getTestingCode() {
		return testingCode;
	}

	public void setTestingCode(String testingCode) {
		this.testingCode = testingCode;
	}

	@Column(name = "input_sample_instance_id")
	public String getInputSampleInstanceId() {
		return inputSampleInstanceId;
	}

	public void setInputSampleInstanceId(String inputSampleInstanceId) {
		this.inputSampleInstanceId = inputSampleInstanceId;
	}

	/*
	 * @JoinColumn(name = "input_sample_instance_id")
	 * 
	 * @OneToOne(fetch = FetchType.EAGER)
	 * 
	 * @NotFound(action = NotFoundAction.IGNORE) public SampleReceiveDetail
	 * getSampleDetail() { return sampleDetail; }
	 * 
	 * public void setSampleDetail(SampleReceiveDetail sampleDetail) {
	 * this.sampleDetail = sampleDetail; }
	 */

	@Column(name = "input_sample_code")
	public String getInputSamplecode() {
		return inputSamplecode;
	}

	public void setInputSamplecode(String inputSamplecode) {
		this.inputSamplecode = inputSamplecode;
	}

	@Column(name = "input_sample_location")
	public String getInputSampleLocation() {
		return inputSampleLocation;
	}

	public void setInputSampleLocation(String inputSampleLocation) {
		this.inputSampleLocation = inputSampleLocation;
	}

	@Column(name = "output_sample_code")
	public String getOutputSamplecode() {
		return outputSamplecode;
	}

	public void setOutputSamplecode(String outputSamplecode) {
		this.outputSamplecode = outputSamplecode;
	}

	@Column(name = "output_sample_location")
	public String getOutputSampleLocation() {
		return outputSampleLocation;
	}

	public void setOutputSampleLocation(String outputSampleLocation) {
		this.outputSampleLocation = outputSampleLocation;
	}

	@JoinColumn(name = "TASK_RESULT_ID")
	@OneToOne(targetEntity = TestingSheetTaskResult.class, fetch = FetchType.EAGER)
	@NotFound(action = NotFoundAction.IGNORE)
	public TestingSheetTaskResult getTaskResult() {
		return taskResult;
	}

	public void setTaskResult(TestingSheetTaskResult taskResult) {
		this.taskResult = taskResult;
	}

	@Column(name = "testing_Shedule_id")
	public Integer getTestingScheduleId() {
		return testingScheduleId;
	}

	public void setTestingScheduleId(Integer testingScheduleId) {
		this.testingScheduleId = testingScheduleId;
	}

	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "connector_id")
	public String getConnectorId() {
		return connectorId;
	}

	public void setConnectorId(String connectorId) {
		this.connectorId = connectorId;
	}

	@Column(name = "sample_detail_id")
	public Integer getSampleDetailId() {
		return sampleDetailId;
	}

	public void setSampleDetailId(Integer sampleDetailId) {
		this.sampleDetailId = sampleDetailId;
	}

	@Column(name = "product_id")
	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	@Column(name = "probe_id")
	public Integer getProbeId() {
		return probeId;
	}

	public void setProbeId(Integer probeId) {
		this.probeId = probeId;
	}

	@Column(name = "probe_name")
	public String getProbeName() {
		return probeName;
	}

	public void setProbeName(String probeName) {
		this.probeName = probeName;
	}

	@Column(name = "concentration")
	public Double getConcentration() {
		return concentration;
	}

	public void setConcentration(Double concentration) {
		this.concentration = concentration;
	}

	@OneToMany(mappedBy = "sheetTask", cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@NotFound(action = NotFoundAction.IGNORE)
	public List<TestingSheetTaskCatch> getSheetTaskCatchList() {
		return sheetTaskCatchList;
	}

	public void setSheetTaskCatchList(
			List<TestingSheetTaskCatch> sheetTaskCatchList) {
		this.sheetTaskCatchList = sheetTaskCatchList;
	}

	@Column(name = "DETECTION_METHOD")
	public String getDetectionMethod() {
		return detectionMethod;
	}

	public void setDetectionMethod(String detectionMethod) {
		this.detectionMethod = detectionMethod;
	}

	@Column(name = "dilute_volume")
	public Double getDiluteVolume() {
		return diluteVolume;
	}

	public void setDiluteVolume(Double diluteVolume) {
		this.diluteVolume = diluteVolume;
	}

	@Column(name = "final_concentration")
	public Double getFinalConcentration() {
		return finalConcentration;
	}

	public void setFinalConcentration(Double finalConcentration) {
		this.finalConcentration = finalConcentration;
	}

	@Column(name = "data_size")
	public Double getDataSize() {
		return dataSize;
	}

	public void setDataSize(Double dataSize) {
		this.dataSize = dataSize;
	}

	@Column(name = "sample_size")
	public Double getSampleSize() {
		return sampleSize;
	}

	public void setSampleSize(Double sampleSize) {
		this.sampleSize = sampleSize;
	}

	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "four_htone")
	public Double getFourHtOne() {
		return fourHtOne;
	}

	public void setFourHtOne(Double fourHtOne) {
		this.fourHtOne = fourHtOne;
	}

	@Column(name = "oneeight_htone")
	public Double getOneEightHtOne() {
		return oneEightHtOne;
	}

	public void setOneEightHtOne(Double oneEightHtOne) {
		this.oneEightHtOne = oneEightHtOne;
	}

	@Column(name = "cluster")
	public Integer getCluster() {
		return cluster;
	}

	public void setCluster(Integer cluster) {
		this.cluster = cluster;
	}

	@Column(name = "original_code")
	public String getOriginalCode() {
		return originalCode;
	}

	public void setOriginalCode(String originalCode) {
		this.originalCode = originalCode;
	}

	@Column(name = "library_code")
	public String getLibraryCode() {
		return libraryCode;
	}

	public void setLibraryCode(String libraryCode) {
		this.libraryCode = libraryCode;
	}

	@Column(name = "coordinate")
	public String getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}

	@Column(name = "product_name")
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@Column(name = "sex")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Column(name = "receive_type")
	public String getReceiveType() {
		return receiveType;
	}

	public void setReceiveType(String receiveType) {
		this.receiveType = receiveType;
	}

	@Column(name = "institution")
	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}

	@Column(name = "project_name")
	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	@Column(name = "file_name")
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Column(name = "case_num")
	public String getCaseNum() {
		return caseNum;
	}

	public void setCaseNum(String caseNum) {
		this.caseNum = caseNum;
	}

	@Column(name = "send_date")
	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	@Column(name = "inspect_name")
	public String getInspectName() {
		return inspectName;
	}

	public void setInspectName(String inspectName) {
		this.inspectName = inspectName;
	}

	@Column(name = "age")
	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Column(name = "check_management")
	public String getCheckManagement() {
		return checkManagement;
	}

	public void setCheckManagement(String checkManagement) {
		this.checkManagement = checkManagement;
	}

	@Column(name = "report_date")
	public Date getReportDate() {
		return reportDate;
	}

	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	@Column(name = "clinical_diagnosis")
	public String getClinicalDiagnosis() {
		return clinicalDiagnosis;
	}

	public void setClinicalDiagnosis(String clinicalDiagnosis) {
		this.clinicalDiagnosis = clinicalDiagnosis;
	}

	@Column(name = "focus_genes")
	public String getFocusGenes() {
		return focusGenes;
	}

	public void setFocusGenes(String focusGenes) {
		this.focusGenes = focusGenes;
	}

	@Column(name = "case_summary")
	public String getCaseSummary() {
		return caseSummary;
	}

	public void setCaseSummary(String caseSummary) {
		this.caseSummary = caseSummary;
	}

	@Column(name = "family_history")
	public String getFamilyHistory() {
		return familyHistory;
	}

	public void setFamilyHistory(String familyHistory) {
		this.familyHistory = familyHistory;
	}

	@Column(name = "family_history_summary")
	public String getFamilyHistorySummary() {
		return familyHistorySummary;
	}

	public void setFamilyHistorySummary(String familyHistorySummary) {
		this.familyHistorySummary = familyHistorySummary;
	}

	@Column(name = "customer_name")
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	@Column(name = "business_leader")
	public String getBusinessLeader() {
		return businessLeader;
	}

	public void setBusinessLeader(String businessLeader) {
		this.businessLeader = businessLeader;
	}

	@Column(name = "technical_leader")
	public String getTechnicalLeader() {
		return technicalLeader;
	}

	public void setTechnicalLeader(String technicalLeader) {
		this.technicalLeader = technicalLeader;
	}

	@Column(name = "report_leader")
	public String getReportLeader() {
		return reportLeader;
	}

	public void setReportLeader(String reportLeader) {
		this.reportLeader = reportLeader;
	}

	@Column(name = "report_complete_date")
	public Date getReportCompleteDate() {
		return reportCompleteDate;
	}

	public void setReportCompleteDate(Date reportCompleteDate) {
		this.reportCompleteDate = reportCompleteDate;
	}

	@Column(name = "report_verifier")
	public String getReportVerifier() {
		return reportVerifier;
	}

	public void setReportVerifier(String reportVerifier) {
		this.reportVerifier = reportVerifier;
	}

	@Column(name = "four_htone_value")
	public Double getFourHtOneValue() {
		return fourHtOneValue;
	}

	public void setFourHtOneValue(Double fourHtOneValue) {
		this.fourHtOneValue = fourHtOneValue;
	}

	@Column(name = "oneeight_htone_value")
	public Double getOneEightHtOneValue() {
		return oneEightHtOneValue;
	}

	public void setOneEightHtOneValue(Double oneEightHtOneValue) {
		this.oneEightHtOneValue = oneEightHtOneValue;
	}

	@Column(name = "absolute_id")
	public Integer getAbsoluteId() {
		return absoluteId;
	}

	public void setAbsoluteId(Integer absoluteId) {
		this.absoluteId = absoluteId;
	}
	
	@Column(name = "serial_code")
	public String getSerialCode() {
		return serialCode;
	}

	public void setSerialCode(String serialCode) {
		this.serialCode = serialCode;
	}

	@Column(name = "input_type")
	public String getInputType() {
		return inputType;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	@Column(name = "input_id")
	public Integer getInputId() {
		return inputId;
	}

	public void setInputId(Integer inputId) {
		this.inputId = inputId;
	}

	@Column(name = "testing_task_id")
	public String getTestingTaskId() {
		return testingTaskId;
	}

	public void setTestingTaskId(String testingTaskId) {
		this.testingTaskId = testingTaskId;
	}

	@Column(name = "products")
	public String getProducts() {
		return products;
	}

	public void setProducts(String products) {
		this.products = products;
	}

	@Column(name = "input_quantity")
	public Double getInputQuantity() {
		return InputQuantity;
	}

	public void setInputQuantity(Double inputQuantity) {
		InputQuantity = inputQuantity;
	}
	
	@Column(name = "output_quantity")
	public Double getOutputQuantity() {
		return outputQuantity;
	}

	public void setOutputQuantity(Double outputQuantity) {
		this.outputQuantity = outputQuantity;
	}
	@Column(name = "notes")
	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}
}
