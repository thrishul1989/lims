package com.todaysoft.lims.sample.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;

import com.todaysoft.lims.persist.AutoKeyEntity;

@Entity
@Table(name = "LIMS_SAMPLE_CHANGE_RECORD")
@Builder(toBuilder = true)
@AllArgsConstructor
public class SampleChangeRecord extends AutoKeyEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7301958531972843013L;

	private Integer sampleTracingId; // 样本跟踪id
	private String changeType; // 变更类型 1-in 2-out
	private Integer changeEvent; // 变更事件 0-样本接收 xxx-sheetTaskId
	private Double changeAmount; // 变更量
	private Date changeTime; // 变更时间
	
	public SampleChangeRecord(){
		
	}

	@Column(name = "SAMPLE_TRACING_ID")
	public Integer getSampleTracingId() {
		return sampleTracingId;
	}

	public void setSampleTracingId(Integer sampleTracingId) {
		this.sampleTracingId = sampleTracingId;
	}

	@Column(name = "CHANGE_TYPE")
	public String getChangeType() {
		return changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

	@Column(name = "CHANGE_EVENT")
	public Integer getChangeEvent() {
		return changeEvent;
	}

	public void setChangeEvent(Integer changeEvent) {
		this.changeEvent = changeEvent;
	}

	@Column(name = "CHANGE_AMOUNT")
	public Double getChangeAmount() {
		return changeAmount;
	}

	public void setChangeAmount(Double changeAmount) {
		this.changeAmount = changeAmount;
	}

	@Column(name = "CHANGE_TIME")
	public Date getChangeTime() {
		return changeTime;
	}

	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}
}
