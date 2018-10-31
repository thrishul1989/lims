package com.todaysoft.lims.system.modules.bpm.ontest.model;

import java.math.BigDecimal;
import java.util.List;

public class SequencingSubmitRequest {
	private String id;

	private Integer cluster;

	private BigDecimal effectiveRate;

	private BigDecimal effectiveSize;

	private BigDecimal q30;

	private String sequecingMachineCode;

	private String run;

	private List<String> lanCode;

	public String getSequecingMachineCode() {
		return sequecingMachineCode;
	}

	public void setSequecingMachineCode(String sequecingMachineCode) {
		this.sequecingMachineCode = sequecingMachineCode;
	}

	public String getRun() {
		return run;
	}

	public void setRun(String run) {
		this.run = run;
	}

	public List<String> getLanCode() {
		return lanCode;
	}

	public void setLanCode(List<String> lanCode) {
		this.lanCode = lanCode;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getCluster() {
		return cluster;
	}

	public void setCluster(Integer cluster) {
		this.cluster = cluster;
	}

	public BigDecimal getEffectiveRate() {
		return effectiveRate;
	}

	public void setEffectiveRate(BigDecimal effectiveRate) {
		this.effectiveRate = effectiveRate;
	}

	public BigDecimal getEffectiveSize() {
		return effectiveSize;
	}

	public void setEffectiveSize(BigDecimal effectiveSize) {
		this.effectiveSize = effectiveSize;
	}

	public BigDecimal getQ30() {
		return q30;
	}

	public void setQ30(BigDecimal q30) {
		this.q30 = q30;
	}
}
