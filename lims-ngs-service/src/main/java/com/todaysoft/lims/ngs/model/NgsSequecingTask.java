package com.todaysoft.lims.ngs.model;

import java.math.BigDecimal;
import java.util.Date;

public class NgsSequecingTask {
	private String id;

	private Date createTime;

	private Date endTime;

	private Integer status;

	private Boolean reSequecing;

	private Integer reSequecingCount;

	private String sequecingCode;

	private BigDecimal dataSize;

	private BigDecimal sequecingCon;

	private BigDecimal firstDiluteCon;

	private BigDecimal firstDiluteSampleInputvolume;

	private BigDecimal firstDiluteHtInputvolume;

	private BigDecimal secondDiluteSampleInputvolume;

	private BigDecimal secondDiluteReagentInputvolume;

	private BigDecimal secondDiluteCon;

	private BigDecimal secondDiluteHtInputvolume;

	private BigDecimal finalCon;

	private BigDecimal finalInputvolume;

	private BigDecimal finalSampleInputvolume;

	private BigDecimal finalHtInputvolume;

	private String run;

	private Integer sequecingType;

	private String sequecingMachineCode;

	private String lanCode;

	private String sequecingStrategy;

	public String getLanCode() {
		return lanCode;
	}

	public void setLanCode(String lanCode) {
		this.lanCode = lanCode;
	}

	public BigDecimal getFinalInputvolume() {
		return finalInputvolume;
	}

	public void setFinalInputvolume(BigDecimal finalInputvolume) {
		this.finalInputvolume = finalInputvolume;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Boolean getReSequecing() {
		return reSequecing;
	}

	public void setReSequecing(Boolean reSequecing) {
		this.reSequecing = reSequecing;
	}

	public Integer getReSequecingCount() {
		return reSequecingCount;
	}

	public void setReSequecingCount(Integer reSequecingCount) {
		this.reSequecingCount = reSequecingCount;
	}

	public String getSequecingCode() {
		return sequecingCode;
	}

	public void setSequecingCode(String sequecingCode) {
		this.sequecingCode = sequecingCode;
	}

	public BigDecimal getDataSize() {
		return dataSize;
	}

	public void setDataSize(BigDecimal dataSize) {
		this.dataSize = dataSize;
	}

	public BigDecimal getSequecingCon() {
		return sequecingCon;
	}

	public void setSequecingCon(BigDecimal sequecingCon) {
		this.sequecingCon = sequecingCon;
	}

	public BigDecimal getFirstDiluteCon() {
		return firstDiluteCon;
	}

	public void setFirstDiluteCon(BigDecimal firstDiluteCon) {
		this.firstDiluteCon = firstDiluteCon;
	}

	public BigDecimal getFirstDiluteSampleInputvolume() {
		return firstDiluteSampleInputvolume;
	}

	public void setFirstDiluteSampleInputvolume(BigDecimal firstDiluteSampleInputvolume) {
		this.firstDiluteSampleInputvolume = firstDiluteSampleInputvolume;
	}

	public BigDecimal getFirstDiluteHtInputvolume() {
		return firstDiluteHtInputvolume;
	}

	public void setFirstDiluteHtInputvolume(BigDecimal firstDiluteHtInputvolume) {
		this.firstDiluteHtInputvolume = firstDiluteHtInputvolume;
	}

	public BigDecimal getSecondDiluteSampleInputvolume() {
		return secondDiluteSampleInputvolume;
	}

	public void setSecondDiluteSampleInputvolume(BigDecimal secondDiluteSampleInputvolume) {
		this.secondDiluteSampleInputvolume = secondDiluteSampleInputvolume;
	}

	public BigDecimal getSecondDiluteReagentInputvolume() {
		return secondDiluteReagentInputvolume;
	}

	public void setSecondDiluteReagentInputvolume(BigDecimal secondDiluteReagentInputvolume) {
		this.secondDiluteReagentInputvolume = secondDiluteReagentInputvolume;
	}

	public BigDecimal getSecondDiluteCon() {
		return secondDiluteCon;
	}

	public void setSecondDiluteCon(BigDecimal secondDiluteCon) {
		this.secondDiluteCon = secondDiluteCon;
	}

	public BigDecimal getSecondDiluteHtInputvolume() {
		return secondDiluteHtInputvolume;
	}

	public void setSecondDiluteHtInputvolume(BigDecimal secondDiluteHtInputvolume) {
		this.secondDiluteHtInputvolume = secondDiluteHtInputvolume;
	}

	public BigDecimal getFinalCon() {
		return finalCon;
	}

	public void setFinalCon(BigDecimal finalCon) {
		this.finalCon = finalCon;
	}

	public BigDecimal getFinalSampleInputvolume() {
		return finalSampleInputvolume;
	}

	public void setFinalSampleInputvolume(BigDecimal finalSampleInputvolume) {
		this.finalSampleInputvolume = finalSampleInputvolume;
	}

	public BigDecimal getFinalHtInputvolume() {
		return finalHtInputvolume;
	}

	public void setFinalHtInputvolume(BigDecimal finalHtInputvolume) {
		this.finalHtInputvolume = finalHtInputvolume;
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

	public String getSequecingStrategy() {
		return sequecingStrategy;
	}

	public void setSequecingStrategy(String sequecingStrategy) {
		this.sequecingStrategy = sequecingStrategy;
	}

}