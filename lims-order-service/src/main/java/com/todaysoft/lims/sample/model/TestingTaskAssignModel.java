package com.todaysoft.lims.sample.model;

import com.todaysoft.lims.sample.entity.TestingTaskDetail;

import java.util.Date;
import java.util.List;

/**
 * Created by HSHY-032 on 2016/8/31.
 */
public class TestingTaskAssignModel {

    private String taskCode;//任务编号

    private String sendName;//下发人

    private Date sendDate;//下发日期

    private Double designDataAmount;//设计数据量

    private int outLibNumber;//出库反应数

    private String wkbhCode;//文库捕获code

    private List<TestingTaskDetail> testingTaskDetailList;
    
    private String inputTypeId;
    
    private Integer inputSampleId;
    
    private Integer taskId;

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

    public Double getDesignDataAmount() {
        return designDataAmount;
    }

    public void setDesignDataAmount(Double designDataAmount) {
        this.designDataAmount = designDataAmount;
    }

    public int getOutLibNumber() {
        return outLibNumber;
    }

    public void setOutLibNumber(int outLibNumber) {
        this.outLibNumber = outLibNumber;
    }

    public List<TestingTaskDetail> getTestingTaskDetailList() {
        return testingTaskDetailList;
    }

    public void setTestingTaskDetailList(List<TestingTaskDetail> testingTaskDetailList) {
        this.testingTaskDetailList = testingTaskDetailList;
    }

    public String getWkbhCode() {
        return wkbhCode;
    }

    public void setWkbhCode(String wkbhCode) {
        this.wkbhCode = wkbhCode;
    }

	public String getInputTypeId() {
		return inputTypeId;
	}

	public void setInputTypeId(String inputTypeId) {
		this.inputTypeId = inputTypeId;
	}

	public Integer getInputSampleId() {
		return inputSampleId;
	}

	public void setInputSampleId(Integer inputSampleId) {
		this.inputSampleId = inputSampleId;
	}

	public Integer getTaskId() {
		return taskId;
	}

	public void setTaskId(Integer taskId) {
		this.taskId = taskId;
	}
}