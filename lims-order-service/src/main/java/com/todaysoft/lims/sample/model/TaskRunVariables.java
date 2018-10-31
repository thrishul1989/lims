package com.todaysoft.lims.sample.model;


import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder(toBuilder = true)
@AllArgsConstructor
public class TaskRunVariables {

	private String outputSampleType; //输出样本类型
	
	private Integer outputSampleId; //输出样本ID
	
	private String outputSampleCode; //输出样本编号
	
	private String outputSampleLocation; //输出样本位置
	
	private String previousSheetTaskId; //上一步的sheetTaskId
	
	private String products; //检测产品
	
	private Date taskCreateDate;//任务生成时间
	
    private String indexCode;//接头编号

	private String wkbhCode;//文库捕获编号
	
	public TaskRunVariables(){
		
	}

	public String getOutputSampleType() {
		return outputSampleType;
	}

	public void setOutputSampleType(String outputSampleType) {
		this.outputSampleType = outputSampleType;
	}

	public Integer getOutputSampleId() {
		return outputSampleId;
	}

	public void setOutputSampleId(Integer outputSampleId) {
		this.outputSampleId = outputSampleId;
	}

	public String getOutputSampleCode() {
		return outputSampleCode;
	}

	public void setOutputSampleCode(String outputSampleCode) {
		this.outputSampleCode = outputSampleCode;
	}

	public String getOutputSampleLocation() {
		return outputSampleLocation;
	}

	public void setOutputSampleLocation(String outputSampleLocation) {
		this.outputSampleLocation = outputSampleLocation;
	}

	public String getPreviousSheetTaskId() {
		return previousSheetTaskId;
	}

	public void setPreviousSheetTaskId(String previousSheetTaskId) {
		this.previousSheetTaskId = previousSheetTaskId;
	}

	public String getProducts() {
		return products;
	}

	public void setProducts(String products) {
		this.products = products;
	}

	public Date getTaskCreateDate() {
		return taskCreateDate;
	}

	public void setTaskCreateDate(Date taskCreateDate) {
		this.taskCreateDate = taskCreateDate;
	}

	public String getIndexCode() {
		return indexCode;
	}

	public void setIndexCode(String indexCode) {
		this.indexCode = indexCode;
	}

	public String getWkbhCode() {
		return wkbhCode;
	}

	public void setWkbhCode(String wkbhCode) {
		this.wkbhCode = wkbhCode;
	}
}
