package com.todaysoft.lims.system.modules.bpm.ontest.model;

import java.math.BigDecimal;

public class SequencingAssignRequest {
	private String id;

	private String reagentKitId;

	private String testerId;

	private String description;

	private BigDecimal firstDiluteConcn;

	private BigDecimal firstDiluteSampleInputVolume;

	private BigDecimal firstDiluteHTInputVolume;

	private BigDecimal secondDiluteConcn;

	private BigDecimal secondDiluteSampleInputVolume;

	private BigDecimal secondDiluteReagentInputVolume;

	private BigDecimal secondDiluteHTInputVolume;

	private BigDecimal finalConcn;

	private BigDecimal finalInputVolume;

	private BigDecimal finalSampleInputVolume;

	private BigDecimal finalHTInputVolume;

	private Integer sequecingType;

	private String sequecingMachineCode;

	private String sequecingStrategy;

	public Integer getSequecingType() {
		return sequecingType;
	}

	public void setSequecingType(Integer sequecingType) {
		this.sequecingType = sequecingType;
	}

	public String getSequecingMachineCode() {
		return sequecingMachineCode;
	}

	public void setSequecingMachineCode(String sequecingMachineCode) {
		this.sequecingMachineCode = sequecingMachineCode;
	}

	public String getSequecingStrategy() {
		return sequecingStrategy;
	}

	public void setSequecingStrategy(String sequecingStrategy) {
		this.sequecingStrategy = sequecingStrategy;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getReagentKitId() {
		return reagentKitId;
	}

	public void setReagentKitId(String reagentKitId) {
		this.reagentKitId = reagentKitId;
	}

	public String getTesterId() {
		return testerId;
	}

	public void setTesterId(String testerId) {
		this.testerId = testerId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getFirstDiluteConcn() {
		return firstDiluteConcn;
	}

	public void setFirstDiluteConcn(BigDecimal firstDiluteConcn) {
		this.firstDiluteConcn = firstDiluteConcn;
	}

	public BigDecimal getFirstDiluteSampleInputVolume() {
		return firstDiluteSampleInputVolume;
	}

	public void setFirstDiluteSampleInputVolume(BigDecimal firstDiluteSampleInputVolume) {
		this.firstDiluteSampleInputVolume = firstDiluteSampleInputVolume;
	}

	public BigDecimal getFirstDiluteHTInputVolume() {
		return firstDiluteHTInputVolume;
	}

	public void setFirstDiluteHTInputVolume(BigDecimal firstDiluteHTInputVolume) {
		this.firstDiluteHTInputVolume = firstDiluteHTInputVolume;
	}

	public BigDecimal getSecondDiluteConcn() {
		return secondDiluteConcn;
	}

	public void setSecondDiluteConcn(BigDecimal secondDiluteConcn) {
		this.secondDiluteConcn = secondDiluteConcn;
	}

	public BigDecimal getSecondDiluteSampleInputVolume() {
		return secondDiluteSampleInputVolume;
	}

	public void setSecondDiluteSampleInputVolume(BigDecimal secondDiluteSampleInputVolume) {
		this.secondDiluteSampleInputVolume = secondDiluteSampleInputVolume;
	}

	public BigDecimal getSecondDiluteReagentInputVolume() {
		return secondDiluteReagentInputVolume;
	}

	public void setSecondDiluteReagentInputVolume(BigDecimal secondDiluteReagentInputVolume) {
		this.secondDiluteReagentInputVolume = secondDiluteReagentInputVolume;
	}

	public BigDecimal getSecondDiluteHTInputVolume() {
		return secondDiluteHTInputVolume;
	}

	public void setSecondDiluteHTInputVolume(BigDecimal secondDiluteHTInputVolume) {
		this.secondDiluteHTInputVolume = secondDiluteHTInputVolume;
	}

	public BigDecimal getFinalConcn() {
		return finalConcn;
	}

	public void setFinalConcn(BigDecimal finalConcn) {
		this.finalConcn = finalConcn;
	}

	public BigDecimal getFinalInputVolume() {
		return finalInputVolume;
	}

	public void setFinalInputVolume(BigDecimal finalInputVolume) {
		this.finalInputVolume = finalInputVolume;
	}

	public BigDecimal getFinalSampleInputVolume() {
		return finalSampleInputVolume;
	}

	public void setFinalSampleInputVolume(BigDecimal finalSampleInputVolume) {
		this.finalSampleInputVolume = finalSampleInputVolume;
	}

	public BigDecimal getFinalHTInputVolume() {
		return finalHTInputVolume;
	}

	public void setFinalHTInputVolume(BigDecimal finalHTInputVolume) {
		this.finalHTInputVolume = finalHTInputVolume;
	}
}
