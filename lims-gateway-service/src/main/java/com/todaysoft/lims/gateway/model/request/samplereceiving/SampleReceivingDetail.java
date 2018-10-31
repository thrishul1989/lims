package com.todaysoft.lims.gateway.model.request.samplereceiving;



import com.todaysoft.lims.persist.UuidKeyEntity;

public class SampleReceivingDetail extends UuidKeyEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String recordId;// '接收记录',
	private String sampleCode;//'样本编号',
	private int mcStatus;//'匹配状态：0-不匹配 1-匹配',
	private int qcStatus;// '质检状态：0-不合格 1-合格',
	private String qcRemark;//'质检备注',
	
	public String getRecordId() {
		return recordId;
	}
	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	public String getSampleCode() {
		return sampleCode;
	}
	public void setSampleCode(String sampleCode) {
		this.sampleCode = sampleCode;
	}
	public int getMcStatus() {
		return mcStatus;
	}
	public void setMcStatus(int mcStatus) {
		this.mcStatus = mcStatus;
	}
	public int getQcStatus() {
		return qcStatus;
	}
	public void setQcStatus(int qcStatus) {
		this.qcStatus = qcStatus;
	}
	public String getQcRemark() {
		return qcRemark;
	}
	public void setQcRemark(String qcRemark) {
		this.qcRemark = qcRemark;
	}
	
	
}
