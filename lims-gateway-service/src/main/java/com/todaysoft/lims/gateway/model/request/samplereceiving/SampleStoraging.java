package com.todaysoft.lims.gateway.model.request.samplereceiving;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.todaysoft.lims.persist.UuidKeyEntity;

public class SampleStoraging extends UuidKeyEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String code ;//''入库编号'
	private Integer type; // '入库类型',
	private String  remark;//   '入库备注',
	private String  operatorId;//  '操作人ID',
	private String   operatorName;//'操作人姓名',
	private Date  operateTime;//'操作时间',
	
	private List<SampleStoragingDetail>  sampleStoragingDetail= new ArrayList<SampleStoragingDetail>(); //转存记录明细
	
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	public Date getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	
	public List<SampleStoragingDetail> getSampleStoragingDetail() {
		return sampleStoragingDetail;
	}
	public void setSampleStoragingDetail(
			List<SampleStoragingDetail> sampleStoragingDetail) {
		this.sampleStoragingDetail = sampleStoragingDetail;
	}
	
	
	

}
