package com.todaysoft.lims.system.model.vo.testingtask;

import java.util.Date;


public class TestingTask {

	

	private String id;
	private String testingName; //实验员名称
	private String description; //描述
	private Date createtime; //创建任务时间
	private String createName; // 输入样本存储位置
	private String taskType; //任务类型
	
	
	
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTestingName() {
		return testingName;
	}
	public void setTestingName(String testingName) {
		this.testingName = testingName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public String getCreateName() {
		return createName;
	}
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	
	
	
	
	
}
