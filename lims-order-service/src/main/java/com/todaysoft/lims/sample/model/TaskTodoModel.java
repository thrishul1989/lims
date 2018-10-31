package com.todaysoft.lims.sample.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by HSHY-032 on 2016/8/16.
 */
public class TaskTodoModel {

    private String sampleInstanceCode;      //接受样本编号

    private Integer productId;  //          //产品id

    private String inspectMan;      //受检者姓名

    private String temporaryStorageLocation;//临时存放位置

    private String name;        //样本类型

    private String outputSamplecode;    //接受样本编号

    private String outputSampleLocation;    //样本存放位置

    private String probeName;       //探针的name

    private String indexCode;       //接头编号

    private Double concn;//文库捕获浓度
    
    private String inputType;
    
    private Integer inputId;
    
    private String sampleType;
    
    private Date sampleReceiveDate;

    private List<Probe> probeList = new ArrayList<>();

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

    public String getTemporaryStorageLocation() {
        return temporaryStorageLocation;
    }

    public void setTemporaryStorageLocation(String temporaryStorageLocation) {
        this.temporaryStorageLocation = temporaryStorageLocation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOutputSamplecode() {
        return outputSamplecode;
    }

    public void setOutputSamplecode(String outputSamplecode) {
        this.outputSamplecode = outputSamplecode;
    }

    public String getOutputSampleLocation() {
        return outputSampleLocation;
    }

    public void setOutputSampleLocation(String outputSampleLocation) {
        this.outputSampleLocation = outputSampleLocation;
    }

    public String getProbeName() {
        return probeName;
    }

    public void setProbeName(String probeName) {
        this.probeName = probeName;
    }

    public String getIndexCode() {
        return indexCode;
    }

    public void setIndexCode(String indexCode) {
        this.indexCode = indexCode;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public List<Probe> getProbeList() {
        return probeList;
    }

    public void setProbeList(List<Probe> probeList) {
        this.probeList = probeList;
    }

    public Double getConcn() {
        return concn;
    }

    public void setConcn(Double concn) {
        this.concn = concn;
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
}
