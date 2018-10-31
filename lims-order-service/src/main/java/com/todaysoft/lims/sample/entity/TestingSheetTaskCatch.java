package com.todaysoft.lims.sample.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import lombok.AllArgsConstructor;
import lombok.Builder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.AutoKeyEntity;

@Entity
@Table(name = "LIMS_TESTING_SHEET_TASK_CATCH")
@Builder(toBuilder = true)
@AllArgsConstructor
public class TestingSheetTaskCatch extends AutoKeyEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4110643250921684782L;

	private TestingSheetTask sheetTask;
	private String activitiTaskId;
	private String inputSampleCode;
	private Double sampleVolume; //混样体积
	private Double concn; //文库浓度
	private String indexCode;
	private String sampleName;
	private String inputSmapleLocation;
	private SampleReceiveDetail sampleDetail;
	private Integer productId;
	
	private Integer probeId; //探针id
	
	public TestingSheetTaskCatch(){
		
	}
	
	@ManyToOne(cascade= CascadeType.ALL,fetch= FetchType.LAZY)
    @JoinColumn(name="SHEET_TASK_ID")
	@JsonIgnore
	public TestingSheetTask getSheetTask() {
		return sheetTask;
	}

	public void setSheetTask(TestingSheetTask sheetTask) {
		this.sheetTask = sheetTask;
	}

	@Column(name="ACTIVITI_TASK_ID")
	public String getActivitiTaskId() {
		return activitiTaskId;
	}

	public void setActivitiTaskId(String activitiTaskId) {
		this.activitiTaskId = activitiTaskId;
	}

	@Column(name="INPUT_SAMPLE_CODE")
	public String getInputSampleCode() {
		return inputSampleCode;
	}

	public void setInputSampleCode(String inputSampleCode) {
		this.inputSampleCode = inputSampleCode;
	}

	@Column(name="SAMPLE_VOLUME")
	public Double getSampleVolume() {
		return sampleVolume;
	}

	public void setSampleVolume(Double sampleVolume) {
		this.sampleVolume = sampleVolume;
	}

	@Column(name="CONCN")
	public Double getConcn() {
		return concn;
	}

	public void setConcn(Double concn) {
		this.concn = concn;
	}

	@Column(name="INDEX_CODE")
	public String getIndexCode() {
		return indexCode;
	}

	public void setIndexCode(String indexCode) {
		this.indexCode = indexCode;
	}

	@Column(name="SAMPLE_NAME")
	public String getSampleName() {
		return sampleName;
	}

	public void setSampleName(String sampleName) {
		this.sampleName = sampleName;
	}

	@Column(name="INPUT_SAMPLE_LOCATION")
	public String getInputSmapleLocation() {
		return inputSmapleLocation;
	}

	public void setInputSmapleLocation(String inputSmapleLocation) {
		this.inputSmapleLocation = inputSmapleLocation;
	}

	@JoinColumn(name = "SAMPLE_DETAIL_ID")
    @OneToOne(targetEntity = SampleReceiveDetail.class, fetch = FetchType.EAGER)
    @NotFound(action = NotFoundAction.IGNORE)
	public SampleReceiveDetail getSampleDetail() {
		return sampleDetail;
	}

	public void setSampleDetail(SampleReceiveDetail sampleDetail) {
		this.sampleDetail = sampleDetail;
	}

	@Column(name="PRODUCT_ID")
	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	@Transient
	public Integer getProbeId() {
		return probeId;
	}

	public void setProbeId(Integer probeId) {
		this.probeId = probeId;
	}
	
	
	
}
