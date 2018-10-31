package com.todaysoft.lims.system.modules.bpm.ontest.model;

import java.math.BigDecimal;
import java.util.Date;

public class NgsSequecingTask implements Comparable<NgsSequecingTask> {
	private String id;

	private Date createTime;

	private Date endTime;

	private Integer status;

	private Boolean ifUrgent;

	private Date urgentUpdateTime;

	private String urgentName;

	private Boolean reSequecing;

	private Integer reSequecingCount;

	private String sequecingCode;

	private BigDecimal dataSize;

	private BigDecimal sequecingCon;

	private BigDecimal finalCon;
	private String run;
	private Integer SEQUECING_TYPE;
	private String SEQUECING_MACHINE_CODE;
	private String SEQUECING_STRATEGY;

	public String getRun() {
		return run;
	}

	public void setRun(String run) {
		this.run = run;
	}

	public Integer getSEQUECING_TYPE() {
		return SEQUECING_TYPE;
	}

	public void setSEQUECING_TYPE(Integer sEQUECING_TYPE) {
		SEQUECING_TYPE = sEQUECING_TYPE;
	}

	public String getSEQUECING_MACHINE_CODE() {
		return SEQUECING_MACHINE_CODE;
	}

	public void setSEQUECING_MACHINE_CODE(String sEQUECING_MACHINE_CODE) {
		SEQUECING_MACHINE_CODE = sEQUECING_MACHINE_CODE;
	}

	public String getSEQUECING_STRATEGY() {
		return SEQUECING_STRATEGY;
	}

	public void setSEQUECING_STRATEGY(String sEQUECING_STRATEGY) {
		SEQUECING_STRATEGY = sEQUECING_STRATEGY;
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

	public BigDecimal getFinalCon() {
		return finalCon;
	}

	public void setFinalCon(BigDecimal finalCon) {
		this.finalCon = finalCon;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getReSequecingCount() {
		return reSequecingCount;
	}

	public void setReSequecingCount(Integer reSequecingCount) {
		this.reSequecingCount = reSequecingCount;
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

	public Boolean getIfUrgent() {
		return ifUrgent;
	}

	public void setIfUrgent(Boolean ifUrgent) {
		this.ifUrgent = ifUrgent;
	}

	public Date getUrgentUpdateTime() {
		return urgentUpdateTime;
	}

	public void setUrgentUpdateTime(Date urgentUpdateTime) {
		this.urgentUpdateTime = urgentUpdateTime;
	}

	public String getUrgentName() {
		return urgentName;
	}

	public void setUrgentName(String urgentName) {
		this.urgentName = urgentName;
	}

	public Boolean getReSequecing() {
		return reSequecing;
	}

	public void setReSequecing(Boolean reSequecing) {
		this.reSequecing = reSequecing;
	}

	@Override
	public int compareTo(NgsSequecingTask o) {
		o.setIfUrgent(null == o.getIfUrgent() ? false : o.getIfUrgent());
		this.setIfUrgent(null == this.getIfUrgent() ? false : this.getIfUrgent());
		if ((this.ifUrgent && o.getIfUrgent()) || (!this.ifUrgent && !o.getIfUrgent())) {
			if (this.getCreateTime().after(o.getCreateTime())) {

				return 1;
			} else {
				return -1;
			}
		} else {

			if (this.getIfUrgent()) {
				return -1;
			} else {
				return 1;
			}
		}

	}
}
