package com.todaysoft.lims.gateway.model.request;

import java.util.Date;
import java.util.List;

import com.todaysoft.lims.gateway.model.TestingSheetTask;
import com.todaysoft.lims.gateway.model.TestingSheetTaskCatch;

public class TestingSheetCreateRequest {
	
	private String code;
	private String semantic;
	private String activitiTaskId;
	private Date createTime;
	private Integer creator;
	private Integer executer;
	private String status;
	private String description;
	private String reagent;
	private String batchNum;
	private String reagentPreInput;
	private List<TestingSheetTask> sheetTaskList;
	
	private String method; //质检方法

	private Double designDataAmount;//设计数据量
	private String reagentCode;//试剂编号
	private Integer outLibNumber;//出库反应数
	private String wkbhCode;//文库捕获下达输入的编号
	

    private String taskCode;//任务编号

    private String sendName;//下发人

    private Date sendDate;//下发日期
    
    private String testingName;//实验负责人
    
   private String outputQuantity;
    
    private String InputQuantity;
    
    private String reagentKitCode;
    
    private String reagentKitName;
    
    private String unit;
    
    private String QTExecuterName;
    
    private Integer QTExecuter;// 质检试验员
    
    
    private Integer reagentKitId;//试剂盒Id
    
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

	public Integer getReagentKitId() {
		return reagentKitId;
	}

	public void setReagentKitId(Integer reagentKitId) {
		this.reagentKitId = reagentKitId;
	}

	public Integer getQTExecuter() {
		return QTExecuter;
	}

	public void setQTExecuter(Integer qTExecuter) {
		QTExecuter = qTExecuter;
	}

	public String getQTExecuterName() {
		return QTExecuterName;
	}

	public void setQTExecuterName(String qTExecuterName) {
		QTExecuterName = qTExecuterName;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getReagentKitCode() {
		return reagentKitCode;
	}

	public void setReagentKitCode(String reagentKitCode) {
		this.reagentKitCode = reagentKitCode;
	}

	public String getReagentKitName() {
		return reagentKitName;
	}

	public void setReagentKitName(String reagentKitName) {
		this.reagentKitName = reagentKitName;
	}

	public String getOutputQuantity() {
		return outputQuantity;
	}

	public void setOutputQuantity(String outputQuantity) {
		this.outputQuantity = outputQuantity;
	}

	public String getInputQuantity() {
		return InputQuantity;
	}

	public void setInputQuantity(String inputQuantity) {
		InputQuantity = inputQuantity;
	}

	public String getTestingName() {
		return testingName;
	}

	public void setTestingName(String testingName) {
		this.testingName = testingName;
	}

	public String getTaskCode() {
		return taskCode;
	}

	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}

	public String getSendName() {
		return sendName;
	}

	public void setSendName(String sendName) {
		this.sendName = sendName;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	private List<TestingSheetTaskCatch> sheetTaskCatchList; //任务单子表（文库捕获）
	
	
	
	public List<TestingSheetTaskCatch> getSheetTaskCatchList() {
		return sheetTaskCatchList;
	}

	public void setSheetTaskCatchList(List<TestingSheetTaskCatch> sheetTaskCatchList) {
		this.sheetTaskCatchList = sheetTaskCatchList;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getSemantic() {
		return semantic;
	}

	public void setSemantic(String semantic) {
		this.semantic = semantic;
	}

	public String getActivitiTaskId() {
		return activitiTaskId;
	}

	public void setActivitiTaskId(String activitiTaskId) {
		this.activitiTaskId = activitiTaskId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getCreator() {
		return creator;
	}

	public void setCreator(Integer creator) {
		this.creator = creator;
	}

	public Integer getExecuter() {
		return executer;
	}

	public void setExecuter(Integer executer) {
		this.executer = executer;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<TestingSheetTask> getSheetTaskList() {
		return sheetTaskList;
	}

	public void setSheetTaskList(List<TestingSheetTask> sheetTaskList) {
		this.sheetTaskList = sheetTaskList;
	}

	public String getReagent() {
		return reagent;
	}

	public void setReagent(String reagent) {
		this.reagent = reagent;
	}



	public String getBatchNum() {
		return batchNum;
	}

	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}

	public String getReagentPreInput() {
		return reagentPreInput;
	}

	public void setReagentPreInput(String reagentPreInput) {
		this.reagentPreInput = reagentPreInput;
	}

	public Double getDesignDataAmount() {
		return designDataAmount;
	}

	public void setDesignDataAmount(Double designDataAmount) {
		this.designDataAmount = designDataAmount;
	}

	public String getReagentCode() {
		return reagentCode;
	}

	public void setReagentCode(String reagentCode) {
		this.reagentCode = reagentCode;
	}

	public Integer getOutLibNumber() {
		return outLibNumber;
	}

	public void setOutLibNumber(Integer outLibNumber) {
		this.outLibNumber = outLibNumber;
	}

	public String getWkbhCode() {
		return wkbhCode;
	}

	public void setWkbhCode(String wkbhCode) {
		this.wkbhCode = wkbhCode;
	}
}
