package com.todaysoft.lims.system.model.vo;

import java.util.ArrayList;
import java.util.List;



public class CheckManagement {

	private Integer id;

	private String name; // 检测方法管理名称
	
	private String description;// 检测管理描述
	
	private List<CheckManagementTask> checkManagementTaskList = new ArrayList<CheckManagementTask>();
	private List<CheckManagerProbe> checkManagerProbeList = new ArrayList<CheckManagerProbe>();
	
	public List<CheckManagerProbe> getCheckManagerProbeList() {
		return checkManagerProbeList;
	}

	public void setCheckManagerProbeList(List<CheckManagerProbe> checkManagerProbeList) {
		this.checkManagerProbeList = checkManagerProbeList;
	}

	private String tasks;// 检测配置
	
	private Integer testSample; // 实验样本
	
	private String analyzeMethodArray;

	private String taskArray;//任务Id数组
	
	private String ifProbe;//是否需要探针
	
	
	
	public String getIfProbe() {
		return ifProbe;
	}

	public void setIfProbe(String ifProbe) {
		this.ifProbe = ifProbe;
	}

	public String getTaskArray() {
		return taskArray;
	}

	public void setTaskArray(String taskArray) {
		this.taskArray = taskArray;
	}

	public Integer getTestSample() {
		return testSample;
	}

	public void setTestSample(Integer testSample) {
		this.testSample = testSample;
	}

	public String getAnalyzeMethodArray() {
		return analyzeMethodArray;
	}

	public void setAnalyzeMethodArray(String analyzeMethodArray) {
		this.analyzeMethodArray = analyzeMethodArray;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	public List<CheckManagementTask> getCheckManagementTaskList() {
		return checkManagementTaskList;
	}

	public void setCheckManagementTaskList(
			List<CheckManagementTask> checkManagementTaskList) {
		this.checkManagementTaskList = checkManagementTaskList;
	}

	public String getTasks() {
		return tasks;
	}

	public void setTasks(String tasks) {
		this.tasks = tasks;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
