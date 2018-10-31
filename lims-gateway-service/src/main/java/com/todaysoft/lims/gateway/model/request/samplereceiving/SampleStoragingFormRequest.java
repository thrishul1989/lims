package com.todaysoft.lims.gateway.model.request.samplereceiving;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;



import com.todaysoft.lims.persist.UuidKeyEntity;

public class SampleStoragingFormRequest extends UuidKeyEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String code ;
	private Integer type; 
	private String  remark;
	private String  operatorId;
	private String   operatorName;
	private Date  operateTime;
	
	private String storagingId; //判断是否已保存
	private String sampleStoragingString; //接收前台JSON字符串
	
	private List<SampleStoragingDetail>  sampleStoragingDetail= new ArrayList<SampleStoragingDetail>(); //转存记录明细
	
	
	
	
	
	public String getStoragingId() {
		return storagingId;
	}
	public void setStoragingId(String storagingId) {
		this.storagingId = storagingId;
	}
	public String getSampleStoragingString() {
		return sampleStoragingString;
	}
	public void setSampleStoragingString(String sampleStoragingString) {
		this.sampleStoragingString = sampleStoragingString;
	}
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
