package com.todaysoft.lims.sample.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by HSHY-032 on 2016/7/27.
 */
public class TodoModel {

    private String testingCode;//实验编号

    private String semantic;//任务类别

    private Object businessObject;

    private String taskActivityId;//流程实例id；
    
    private Integer testingTaskId;
    
    private Integer taskState;//任务状态，是否异常
    private String sampleInstanceCode;      //接受样本编号
    private String inspectMan;      //受检者姓名
    private String inputType;
    private Integer inputId;
    private String sampleType;
    private Date sampleReceiveDate;
    private String products;
    private Date taskCreateDate;//任务生成时间
    private String indexCode;//接头编号
    private String receiveType;//接收类型/订单类型
    private List<String>  sampleInstanceCodeList= new ArrayList<String>();
    public List<String> getSampleInstanceCodeList() {
		return sampleInstanceCodeList;
	}

	public void setSampleInstanceCodeList(List<String> sampleInstanceCodeList) {
		this.sampleInstanceCodeList = sampleInstanceCodeList;
	}

	public String getReceiveType() {
		return receiveType;
	}

	public void setReceiveType(String receiveType) {
		this.receiveType = receiveType;
	}

	public String getTestingCode() {
        return testingCode;
    }

    public void setTestingCode(String testingCode) {
        this.testingCode = testingCode;
    }

    public String getSemantic() {
        return semantic;
    }

    public void setSemantic(String semantic) {
        this.semantic = semantic;
    }

    public Object getBusinessObject() {
        return businessObject;
    }

    public void setBusinessObject(Object businessObject) {
        this.businessObject = businessObject;
    }

    public String getTaskActivityId() {
        return taskActivityId;
    }

    public void setTaskActivityId(String taskActivityId) {
        this.taskActivityId = taskActivityId;
    }

	public Integer getTestingTaskId() {
		return testingTaskId;
	}

	public void setTestingTaskId(Integer testingTaskId) {
		this.testingTaskId = testingTaskId;
	}

	public String getSampleInstanceCode() {
		return sampleInstanceCode;
	}

	public void setSampleInstanceCode(String sampleInstanceCode) {
		this.sampleInstanceCode = sampleInstanceCode;
	}

	public String getInspectMan() {
		return inspectMan;
	}

	public void setInspectMan(String inspectMan) {
		this.inspectMan = inspectMan;
	}

	public String getInputType() {
		return inputType;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	public Integer getInputId() {
		return inputId;
	}

	public void setInputId(Integer inputId) {
		this.inputId = inputId;
	}

	public String getSampleType() {
		return sampleType;
	}

	public void setSampleType(String sampleType) {
		this.sampleType = sampleType;
	}

	public Date getSampleReceiveDate() {
		return sampleReceiveDate;
	}

	public void setSampleReceiveDate(Date sampleReceiveDate) {
		this.sampleReceiveDate = sampleReceiveDate;
	}

	public Integer getTaskState() {
		return taskState;
	}

	public void setTaskState(Integer taskState) {
		this.taskState = taskState;
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
}
