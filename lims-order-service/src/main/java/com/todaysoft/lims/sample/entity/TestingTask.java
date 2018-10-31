package com.todaysoft.lims.sample.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.todaysoft.lims.persist.AutoKeyEntity;

@Entity
@Table(name = "LIMS_TESTING_TASK")
public class TestingTask extends AutoKeyEntity {

	private static final long serialVersionUID = 1L;
    
	private String name;        //任务中文名
	private String semantic;
	private Date startTime;
	private Integer status;
	private Date endTime;
	private Integer endType;
	private Integer taskState;
	
	private String inputSampleId;

	@Column(name="NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name="SEMANTIC")
	public String getSemantic() {
		return semantic;
	}

	public void setSemantic(String semantic) {
		this.semantic = semantic;
	}

	@Column(name="START_TIME")
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Column(name="STATUS")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name="END_TIME")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name="END_TYPE")
	public Integer getEndType() {
		return endType;
	}

	public void setEndType(Integer endType) {
		this.endType = endType;
	}

	@Column(name="TASK_STATE")
	public Integer getTaskState() {
		return taskState;
	}

	public void setTaskState(Integer taskState) {
		this.taskState = taskState;
	}

	@Column(name="INPUT_SAMPLE_ID")
    public String getInputSampleId()
    {
        return inputSampleId;
    }

    public void setInputSampleId(String inputSampleId)
    {
        this.inputSampleId = inputSampleId;
    }
	
	
}
