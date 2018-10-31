package com.todaysoft.lims.maintain.entity;

import com.todaysoft.lims.persist.UuidKeyEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "NGS_SEQUECING_TASK")
public class NgsSequecingTask extends UuidKeyEntity {

	private Date createTime;

	private Integer status;

	private Boolean reSequecing;

	private String sequecingCode;

	private BigDecimal dataSize;

	private BigDecimal sequecingCon;

	@Column(name = "CREATE_TIME")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

}
