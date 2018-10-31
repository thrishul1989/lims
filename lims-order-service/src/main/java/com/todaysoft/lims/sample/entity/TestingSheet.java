package com.todaysoft.lims.sample.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.todaysoft.lims.persist.AutoKeyEntity;
import com.todaysoft.lims.persist.QueryField;

import lombok.AllArgsConstructor;
import lombok.Builder;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "LIMS_TESTING_SHEET")
@Builder(toBuilder = true)
@AllArgsConstructor
public class TestingSheet extends AutoKeyEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 710167645781569694L;

	private String code;
	@QueryField(type = QueryField.QueryType.eq)
	private String semantic;
	private Integer taskId;
	private String taskName;
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
	private String method; // 质检方法
	private Double designDataAmount;// 设计数据量
	private String reagentCode;// 试剂编号
	private Integer outLibNumber;// 出库反应数
	private String wkbhCode;// 文库捕获下达输入的code

	private String sendName;// 下发人

	private Date sendDate;// 下发日期

	private String testingName;// 实验负责人

	private String outputQuantity;

	private String InputQuantity;

	private String reagentKitCode;

	private String reagentKitName;

	private String unit;

	private Integer QTExecuter;// 质检试验员
	
	private String QTExecuterName;//质检员名称
	
	private String variables;//json变量

	public TestingSheet() {

	}

	@Column(name = "CODE")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Column(name = "SEMANTIC")
	public String getSemantic() {
		return semantic;
	}

	public void setSemantic(String semantic) {
		this.semantic = semantic;
	}

	@Column(name = "TASK_ID")
	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}

	@Column(name = "TASK_NAME")
	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	@Column(name = "ACTIVITI_TASK_ID")
	public String getActivitiTaskId() {
		return activitiTaskId;
	}

	public void setActivitiTaskId(String activitiTaskId) {
		this.activitiTaskId = activitiTaskId;
	}

	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "CREATOR")
	public Integer getCreator() {
		return creator;
	}

	public void setCreator(Integer creator) {
		this.creator = creator;
	}

	@Column(name = "EXECUTER")
	public Integer getExecuter() {
		return executer;
	}

	public void setExecuter(Integer executer) {
		this.executer = executer;
	}

	@Column(name = "STATUS")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@OneToMany(mappedBy = "testingSheet", cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@NotFound(action = NotFoundAction.IGNORE)
	public List<TestingSheetTask> getSheetTaskList() {
		return sheetTaskList;
	}

	public void setSheetTaskList(List<TestingSheetTask> sheetTaskList) {
		this.sheetTaskList = sheetTaskList;
	}

	@Column(name = "METHOD")
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	@Column(name = "REAGENT")
	public String getReagent() {
		return reagent;
	}

	public void setReagent(String reagent) {
		this.reagent = reagent;
	}

	@Column(name = "batch_num")
	public String getBatchNum() {
		return batchNum;
	}

	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}

	@Column(name = "reagent_pre_input")
	public String getReagentPreInput() {
		return reagentPreInput;
	}

	public void setReagentPreInput(String reagentPreInput) {
		this.reagentPreInput = reagentPreInput;
	}

	@Column(name = "design_data_amount")
	public Double getDesignDataAmount() {
		return designDataAmount;
	}

	public void setDesignDataAmount(Double designDataAmount) {
		this.designDataAmount = designDataAmount;
	}

	@Column(name = "reagent_code")
	public String getReagentCode() {
		return reagentCode;
	}

	public void setReagentCode(String reagentCode) {
		this.reagentCode = reagentCode;
	}

	@Column(name = "out_lib_number")
	public Integer getOutLibNumber() {
		return outLibNumber;
	}

	public void setOutLibNumber(Integer outLibNumber) {
		this.outLibNumber = outLibNumber;
	}

	@Column(name = "wkbh_code")
	public String getWkbhCode() {
		return wkbhCode;
	}

	public void setWkbhCode(String wkbhCode) {
		this.wkbhCode = wkbhCode;
	}

	@Column(name = "send_name")
	public String getSendName() {
		return sendName;
	}

	public void setSendName(String sendName) {
		this.sendName = sendName;
	}

	@Column(name = "send_date")
	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	@Column(name = "testing_name")
	public String getTestingName() {
		return testingName;
	}

	public void setTestingName(String testingName) {
		this.testingName = testingName;
	}

	@Column(name = "Input_Quantity")
	public String getInputQuantity() {
		return InputQuantity;
	}

	public void setInputQuantity(String inputQuantity) {
		this.InputQuantity = inputQuantity;
	}

	@Column(name = "output_Quantity")
	public String getOutputQuantity() {
		return outputQuantity;
	}

	public void setOutputQuantity(String outputQuantity) {
		this.outputQuantity = outputQuantity;
	}

	@Column(name = "reagentKit_code")
	public String getReagentKitCode() {
		return reagentKitCode;
	}

	public void setReagentKitCode(String reagentKitCode) {
		this.reagentKitCode = reagentKitCode;
	}

	@Column(name = "reagentKit_name")
	public String getReagentKitName() {
		return reagentKitName;
	}

	public void setReagentKitName(String reagentKitName) {
		this.reagentKitName = reagentKitName;
	}

	@Column(name = "unit")
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Column(name = "QT_executer")
	public Integer getQTExecuter() {
		return QTExecuter;
	}

	public void setQTExecuter(Integer qTExecuter) {
		QTExecuter = qTExecuter;
	}
	@Column(name = "QT_executer_name")
	public String getQTExecuterName() {
		return QTExecuterName;
	}

	public void setQTExecuterName(String qTExecuterName) {
		QTExecuterName = qTExecuterName;
	}

	@Column(name = "VARIABLES")
    public String getVariables()
    {
        return variables;
    }
    
    public void setVariables(String variables)
    {
        this.variables = variables;
    }
}
