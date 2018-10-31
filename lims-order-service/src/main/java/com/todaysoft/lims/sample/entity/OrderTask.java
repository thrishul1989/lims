package com.todaysoft.lims.sample.entity;

import com.todaysoft.lims.persist.AutoKeyEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "LIMS_ORDER_TASK")
public class OrderTask extends AutoKeyEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7549732932548300506L;

	private Integer orderId; //
	private Integer sampleDetailId; //样本明细
	private Integer taskId; //
	private String taskName; //
	private String inputType; // 输入类型
	private Integer inputKey; // 输入实例
	private String inputLocation; // 输入样本存储位置
	private Double inputAmount; // 输入量
	private String inputUnit; //投入单位
	private String outputType; // 输出类型
	private Integer outputKey; // 输出实例
	private String outputLocation; // 输出产物存储位置
	private Double outputAmount; // 输出量
	private String outputUnit; //产物单位

	@Column(name = "ORDER_ID")
	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	
	@Column(name = "SAMPLE_DETAIL_ID")
	public Integer getSampleDetailId() {
		return sampleDetailId;
	}

	public void setSampleDetailId(Integer sampleDetailId) {
		this.sampleDetailId = sampleDetailId;
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

	@Column(name = "INPUT_TYPE")
	public String getInputType() {
		return inputType;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	@Column(name = "INPUT_KEY")
	public Integer getInputKey() {
		return inputKey;
	}

	public void setInputKey(Integer inputKey) {
		this.inputKey = inputKey;
	}

	@Column(name = "INPUT_LOCATION")
	public String getInputLocation() {
		return inputLocation;
	}

	public void setInputLocation(String inputLocation) {
		this.inputLocation = inputLocation;
	}

	@Column(name = "INPUT_AMOUNT")
	public Double getInputAmount() {
		return inputAmount;
	}

	public void setInputAmount(Double inputAmount) {
		this.inputAmount = inputAmount;
	}

	@Column(name = "INPUT_UNIT")
	public String getInputUnit() {
		return inputUnit;
	}

	public void setInputUnit(String inputUnit) {
		this.inputUnit = inputUnit;
	}

	@Column(name = "OUTPUT_TYPE")
	public String getOutputType() {
		return outputType;
	}

	public void setOutputType(String outputType) {
		this.outputType = outputType;
	}

	@Column(name = "OUTPUT_KEY")
	public Integer getOutputKey() {
		return outputKey;
	}

	public void setOutputKey(Integer outputKey) {
		this.outputKey = outputKey;
	}

	@Column(name = "OUTPUT_LOCATION")
	public String getOutputLocation() {
		return outputLocation;
	}

	public void setOutputLocation(String outputLocation) {
		this.outputLocation = outputLocation;
	}

	@Column(name = "OUTPUT_AMOUNT")
	public Double getOutputAmount() {
		return outputAmount;
	}

	public void setOutputAmount(Double outputAmount) {
		this.outputAmount = outputAmount;
	}

	@Column(name = "OUTPUT_UNIT")
	public String getOutputUnit() {
		return outputUnit;
	}

	public void setOutputUnit(String outputUnit) {
		this.outputUnit = outputUnit;
	}
}
