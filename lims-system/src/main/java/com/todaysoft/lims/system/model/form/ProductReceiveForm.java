package com.todaysoft.lims.system.model.form;

/**
 * @Description: TODO @author： 沈从会 @Date： 2016年7月4日 上午10:15:10
 */
public class ProductReceiveForm {

	private Integer id;

	private String code; // 产品编号

	private String name; // 产品名称

	private Double price; // 产品定价￥

	private Double cost; // 产品成本

	private Integer cycle; // 周期 天

	private Integer detectionClassfy; // 检测分类

	private String testMethod; // 检测方法

	private String enSample;//推荐样本

	private String description;// 产品描述

	private String testSample;// 实验样本列表

	private String tasks;// 任务列表

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Integer getCycle() {
		return cycle;
	}

	public void setCycle(Integer cycle) {
		this.cycle = cycle;
	}

	public Integer getDetectionClassfy() {
		return detectionClassfy;
	}

	public void setDetectionClassfy(Integer detectionClassfy) {
		this.detectionClassfy = detectionClassfy;
	}

	public String getTestMethod() {
		return testMethod;
	}

	public void setTestMethod(String testMethod) {
		this.testMethod = testMethod;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTestSample() {
		return testSample;
	}

	public void setTestSample(String testSample) {
		this.testSample = testSample;
	}

	public String getTasks() {
		return tasks;
	}

	public void setTasks(String tasks) {
		this.tasks = tasks;
	}

	public String getEnSample() {
		return enSample;
	}

	public void setEnSample(String enSample) {
		this.enSample = enSample;
	}

}
