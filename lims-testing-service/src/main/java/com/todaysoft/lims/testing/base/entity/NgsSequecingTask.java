package com.todaysoft.lims.testing.base.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "NGS_SEQUECING_TASK")
public class NgsSequecingTask implements Serializable {
    
    private static final long serialVersionUID = -4934423728159240719L;

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

	@Column(name = "LAN_CODE")
	public String getLanCode() {
		return lanCode;
	}

	public void setLanCode(String lanCode) {
		this.lanCode = lanCode;
	}

	@Column(name = "FINAL_INPUTVOLUME")
	public BigDecimal getFinalInputvolume() {
		return finalInputvolume;
	}

	public void setFinalInputvolume(BigDecimal finalInputvolume) {
		this.finalInputvolume = finalInputvolume;
	}

	@Id
    @Column(name = "ID")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "END_TIME")
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name = "STATUS")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "RE_SEQUECING")
	public Boolean getReSequecing() {
		return reSequecing;
	}

	public void setReSequecing(Boolean reSequecing) {
		this.reSequecing = reSequecing;
	}

	@Column(name = "RE_SEQUECING_COUNT")
	public Integer getReSequecingCount() {
		return reSequecingCount;
	}

	public void setReSequecingCount(Integer reSequecingCount) {
		this.reSequecingCount = reSequecingCount;
	}

	@Column(name = "SEQUECING_CODE")
	public String getSequecingCode() {
		return sequecingCode;
	}

	public void setSequecingCode(String sequecingCode) {
		this.sequecingCode = sequecingCode;
	}

	@Column(name = "DATA_SIZE")
	public BigDecimal getDataSize() {
		return dataSize;
	}

	public void setDataSize(BigDecimal dataSize) {
		this.dataSize = dataSize;
	}

	@Column(name = "SEQUECING_CON")
	public BigDecimal getSequecingCon() {
		return sequecingCon;
	}

	public void setSequecingCon(BigDecimal sequecingCon) {
		this.sequecingCon = sequecingCon;
	}

	@Column(name = "FIRST_DILUTE_CON")
	public BigDecimal getFirstDiluteCon() {
		return firstDiluteCon;
	}

	public void setFirstDiluteCon(BigDecimal firstDiluteCon) {
		this.firstDiluteCon = firstDiluteCon;
	}

	@Column(name = "FIRST_DILUTE_SAMPLE_INPUTVOLUME")
	public BigDecimal getFirstDiluteSampleInputvolume() {
		return firstDiluteSampleInputvolume;
	}

	public void setFirstDiluteSampleInputvolume(BigDecimal firstDiluteSampleInputvolume) {
		this.firstDiluteSampleInputvolume = firstDiluteSampleInputvolume;
	}

	@Column(name = "FIRST_DILUTE_HT_INPUTVOLUME")
	public BigDecimal getFirstDiluteHtInputvolume() {
		return firstDiluteHtInputvolume;
	}

	public void setFirstDiluteHtInputvolume(BigDecimal firstDiluteHtInputvolume) {
		this.firstDiluteHtInputvolume = firstDiluteHtInputvolume;
	}

	@Column(name = "SECOND_DILUTE_SAMPLE_INPUTVOLUME")
	public BigDecimal getSecondDiluteSampleInputvolume() {
		return secondDiluteSampleInputvolume;
	}

	public void setSecondDiluteSampleInputvolume(BigDecimal secondDiluteSampleInputvolume) {
		this.secondDiluteSampleInputvolume = secondDiluteSampleInputvolume;
	}

	@Column(name = "SECOND_DILUTE_REAGENT_INPUTVOLUME")
	public BigDecimal getSecondDiluteReagentInputvolume() {
		return secondDiluteReagentInputvolume;
	}

	public void setSecondDiluteReagentInputvolume(BigDecimal secondDiluteReagentInputvolume) {
		this.secondDiluteReagentInputvolume = secondDiluteReagentInputvolume;
	}

	@Column(name = "SECOND_DILUTE_CON")
	public BigDecimal getSecondDiluteCon() {
		return secondDiluteCon;
	}

	public void setSecondDiluteCon(BigDecimal secondDiluteCon) {
		this.secondDiluteCon = secondDiluteCon;
	}

	@Column(name = "SECOND_DILUTE_HT_INPUTVOLUME")
	public BigDecimal getSecondDiluteHtInputvolume() {
		return secondDiluteHtInputvolume;
	}

	public void setSecondDiluteHtInputvolume(BigDecimal secondDiluteHtInputvolume) {
		this.secondDiluteHtInputvolume = secondDiluteHtInputvolume;
	}

	@Column(name = "FINAL_CON")
	public BigDecimal getFinalCon() {
		return finalCon;
	}

	public void setFinalCon(BigDecimal finalCon) {
		this.finalCon = finalCon;
	}

	@Column(name = "FINAL_SAMPLE_INPUTVOLUME")
	public BigDecimal getFinalSampleInputvolume() {
		return finalSampleInputvolume;
	}

	public void setFinalSampleInputvolume(BigDecimal finalSampleInputvolume) {
		this.finalSampleInputvolume = finalSampleInputvolume;
	}

	@Column(name = "FINAL_HT_INPUTVOLUME")
	public BigDecimal getFinalHtInputvolume() {
		return finalHtInputvolume;
	}

	public void setFinalHtInputvolume(BigDecimal finalHtInputvolume) {
		this.finalHtInputvolume = finalHtInputvolume;
	}

	@Column(name = "RUN")
	public String getRun() {
		return run;
	}

	public void setRun(String run) {
		this.run = run;
	}

	@Column(name = "SEQUECING_TYPE")
	public Integer getSequecingType() {
		return sequecingType;
	}

	public void setSequecingType(Integer sequecingType) {
		this.sequecingType = sequecingType;
	}

	@Column(name = "SEQUECING_MACHINE_CODE")
	public String getSequecingMachineCode() {
		return sequecingMachineCode;
	}

	public void setSequecingMachineCode(String sequecingMachineCode) {
		this.sequecingMachineCode = sequecingMachineCode;
	}

	@Column(name = "SEQUECING_STRATEGY")
	public String getSequecingStrategy() {
		return sequecingStrategy;
	}

	public void setSequecingStrategy(String sequecingStrategy) {
		this.sequecingStrategy = sequecingStrategy;
	}

}