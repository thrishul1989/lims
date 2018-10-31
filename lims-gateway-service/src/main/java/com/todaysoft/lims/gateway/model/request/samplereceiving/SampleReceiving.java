package com.todaysoft.lims.gateway.model.request.samplereceiving;

import java.util.Date;


import com.todaysoft.lims.persist.UuidKeyEntity;

public class SampleReceiving extends UuidKeyEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String code ;//'接收编号'
	private String  orderId;// '关联订单',
	private int status;// '接收状态：0-异常 1-正常',
	private String remark;// '接收备注',
	private String  receiverId ;// '接收人ID',
	private String  receiverName;//'接收人姓名',
	private Date  receiveTime;// '接收时间',
	private Integer pageSize;
	private Integer pageNo;
	
	
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	public String getCode() {
		return code;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public Date getReceiveTime() {
		return receiveTime;
	}
	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}

}
