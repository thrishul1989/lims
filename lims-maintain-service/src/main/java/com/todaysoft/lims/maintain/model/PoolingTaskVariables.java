package com.todaysoft.lims.maintain.model;

import java.math.BigDecimal;

public class PoolingTaskVariables {
	private String testingCode;

	private BigDecimal ctv;

	private BigDecimal poolingConcn;

	private BigDecimal testingDatasize;

	private BigDecimal rqtv;

	private BigDecimal relativeVolume;

	private BigDecimal globalAdjustVolume;

	private BigDecimal specifiedRatio;

	private BigDecimal mixVolume;

	public String getTestingCode() {
		return testingCode;
	}

	public void setTestingCode(String testingCode) {
		this.testingCode = testingCode;
	}

	public BigDecimal getCtv() {
		return ctv;
	}

	public void setCtv(BigDecimal ctv) {
		this.ctv = ctv;
	}

	public BigDecimal getPoolingConcn() {
		return poolingConcn;
	}

	public void setPoolingConcn(BigDecimal poolingConcn) {
		this.poolingConcn = poolingConcn;
	}

	public BigDecimal getTestingDatasize() {
		return testingDatasize;
	}

	public void setTestingDatasize(BigDecimal testingDatasize) {
		this.testingDatasize = testingDatasize;
	}

	public BigDecimal getRqtv() {
		return rqtv;
	}

	public void setRqtv(BigDecimal rqtv) {
		this.rqtv = rqtv;
	}

	public BigDecimal getRelativeVolume() {
		return relativeVolume;
	}

	public void setRelativeVolume(BigDecimal relativeVolume) {
		this.relativeVolume = relativeVolume;
	}

	public BigDecimal getGlobalAdjustVolume() {
		return globalAdjustVolume;
	}

	public void setGlobalAdjustVolume(BigDecimal globalAdjustVolume) {
		this.globalAdjustVolume = globalAdjustVolume;
	}

	public BigDecimal getSpecifiedRatio() {
		return specifiedRatio;
	}

	public void setSpecifiedRatio(BigDecimal specifiedRatio) {
		this.specifiedRatio = specifiedRatio;
	}

	public BigDecimal getMixVolume() {
		return mixVolume;
	}

	public void setMixVolume(BigDecimal mixVolume) {
		this.mixVolume = mixVolume;
	}
}
