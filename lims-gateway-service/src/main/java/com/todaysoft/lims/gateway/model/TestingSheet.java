package com.todaysoft.lims.gateway.model;

import java.util.Date;
import java.util.List;

public class TestingSheet {

	private Integer id;
	private String code;
	private String semantic;
	private Integer taskId;
	private String taskName;
	private String activitiTaskId;
	private Date createTime;
	private Integer creator;
	private Integer executer;
	private String status;
	private String description;
	private List<TestingSheetTask> sheetTaskList;
	private String wkbhCode;
	private String sendName;// 下发人
	private String reagent;
	private Double designDataAmount;// 设计数据量
	private String reagentCode;// 试剂编号
	private Integer outLibNumber;// 出库反应数
	private String testingName;// 实验负责人
	private Date sendDate;// 下达时间
	private String method;// 质检方法
	private String outputQuantity;

	private String InputQuantity;
	private String unit;
    private String reagentKitCode;
    
    private String reagentKitName;
    
    private Integer QTExecuter;// 质检试验员
    
    private String QTExecuterName;//质检员名称
    private String batchNum;
    
    
    
	public String getBatchNum() {
		return batchNum;
	}

	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
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

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getReagent() {
		return reagent;
	}

	public void setReagent(String reagent) {
		this.reagent = reagent;
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

	public String getSendName() {
		return sendName;
	}

	public void setSendName(String sendName) {
		this.sendName = sendName;
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

	public String getSemantic() {
		return semantic;
	}

	public void setSemantic(String semantic) {
		this.semantic = semantic;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
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

	public String getWkbhCode() {
		return wkbhCode;
	}

	public void setWkbhCode(String wkbhCode) {
		this.wkbhCode = wkbhCode;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Integer getQTExecuter() {
		return QTExecuter;
	}

	public void setQTExecuter(Integer qTExecuter) {
		QTExecuter = qTExecuter;
	}
}
