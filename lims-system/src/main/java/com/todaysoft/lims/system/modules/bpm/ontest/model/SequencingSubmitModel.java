package com.todaysoft.lims.system.modules.bpm.ontest.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.google.common.collect.Lists;
import com.todaysoft.lims.system.modules.bpm.samplestock.model.TestingSheetTask;

public class SequencingSubmitModel {
	private String id;

	private String code;

	private String reagentKitName;

	private String assignerName;

	private Date assignTime;

	private String description;

	private String sampleCode;

	private BigDecimal concn;

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

	private String sequecingstrategy;

	private List<String> lanCode;

	private String run;

	private String testerName;
	private Date submitTime;

	public String getTesterName() {
		return testerName;
	}

	public void setTesterName(String testerName) {
		this.testerName = testerName;
	}

	public Date getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	public String getRun() {
		return run;
	}

	public void setRun(String run) {
		this.run = run;
	}

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

	public String getSequecingstrategy() {
		return sequecingstrategy;
	}

	public void setSequecingstrategy(String sequecingstrategy) {
		this.sequecingstrategy = sequecingstrategy;
	}

	public List<String> getLanCode() {
		return lanCode;
	}

	public void setLanCode(List<String> lanCode) {
		this.lanCode = lanCode;
	}

	private List<TestingSheetTask> testingSheetTaskList = Lists.newArrayList();

	public List<TestingSheetTask> getTestingSheetTaskList() {
		return testingSheetTaskList;
	}

	public void setTestingSheetTaskList(List<TestingSheetTask> testingSheetTaskList) {
		this.testingSheetTaskList = testingSheetTaskList;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getReagentKitName() {
		return reagentKitName;
	}

	public void setReagentKitName(String reagentKitName) {
		this.reagentKitName = reagentKitName;
	}

	public String getAssignerName() {
		return assignerName;
	}

	public void setAssignerName(String assignerName) {
		this.assignerName = assignerName;
	}

	public Date getAssignTime() {
		return assignTime;
	}

	public void setAssignTime(Date assignTime) {
		this.assignTime = assignTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSampleCode() {
		return sampleCode;
	}

	public void setSampleCode(String sampleCode) {
		this.sampleCode = sampleCode;
	}

	public BigDecimal getConcn() {
		return concn;
	}

	public void setConcn(BigDecimal concn) {
		this.concn = concn;
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
