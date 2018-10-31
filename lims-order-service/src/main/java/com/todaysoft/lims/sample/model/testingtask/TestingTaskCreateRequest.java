package com.todaysoft.lims.sample.model.testingtask;

import java.util.Date;

import com.todaysoft.lims.sample.entity.TestingTask;

public class TestingTaskCreateRequest extends TestingTask{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//旧表相关属性
	private String detailId;
	private String testCode; //实验编号
	private String testedName; //受检人姓名
	private String sourceSampleCode; //源样本编号
	private String sourceSampleType; //源样本类型
	private String sourceSampleLocation; //源样本位置
	private String targetSampleCode; // 目标样本编号
	private String targetSampleLocation; //目标样本位置
	private String testTaskId; //下达任务主键
	private String serialCode;//编号
	
	/*//根据新表新增属性
	private Integer orderId;    //订单 
	private Integer productId;  //检测项目
	private Integer methodId;   //检测方法
	private String name;        //任务中文名
	private String semantic;
	private Date startTime;
	private Integer status;
	private Date endTime;
	private Integer endType;
	*/
	
	private Integer id;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSerialCode() {
		return serialCode;
	}
	public void setSerialCode(String serialCode) {
		this.serialCode = serialCode;
	}
	public String getDetailId() {
		return detailId;
	}
	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}
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
	/*public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Integer getMethodId() {
		return methodId;
	}
	public void setMethodId(Integer methodId) {
		this.methodId = methodId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSemantic() {
		return semantic;
	}
	public void setSemantic(String semantic) {
		this.semantic = semantic;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Integer getEndType() {
		return endType;
	}
	public void setEndType(Integer endType) {
		this.endType = endType;
	}
	*/
}
