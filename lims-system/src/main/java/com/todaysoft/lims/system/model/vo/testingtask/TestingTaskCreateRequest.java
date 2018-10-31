package com.todaysoft.lims.system.model.vo.testingtask;


public class TestingTaskCreateRequest  extends TestingTask{
	
	
	
	private String detailId;
	private String testCode; //实验编号
	private String testedName; //受检人姓名
	private String sourceSampleCode; //源样本编号
	private String sourceSampleType; //源样本类型
	private String sourceSampleLocation; //源样本位置
	private String targetSampleCode; // 目标样本编号
	private String targetSampleLocation; //目标样本位置
	private String testTaskId; //下达任务主键
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
	
	
	
}
