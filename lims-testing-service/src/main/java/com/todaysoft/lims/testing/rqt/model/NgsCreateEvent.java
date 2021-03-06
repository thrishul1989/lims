package com.todaysoft.lims.testing.rqt.model;

import java.math.BigDecimal;
import java.util.List;

public class NgsCreateEvent {

	private String sequecingCode;
	private BigDecimal dataSize;
	private BigDecimal sequecingCon;

	private String qtTaskId;

	public String getQtTaskId() {
		return qtTaskId;
	}

	public void setQtTaskId(String qtTaskId) {
		this.qtTaskId = qtTaskId;
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

}
