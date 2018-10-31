package com.todaysoft.lims.gateway.model.request.samplereceiving;




import com.todaysoft.lims.persist.UuidKeyEntity;

public class SampleStoragingDetail extends UuidKeyEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String sampleCode;//    '样本编号',
	private String location;//位置
    
    private SampleStoraging sampleStoraging; 
	
	public String getSampleCode() {
		return sampleCode;
	}
	public void setSampleCode(String sampleCode) {
		this.sampleCode = sampleCode;
	}
	
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	public SampleStoraging getSampleStoraging() {
		return sampleStoraging;
	}
	public void setSampleStoraging(SampleStoraging sampleStoraging) {
		this.sampleStoraging = sampleStoraging;
	}
	
	
	

	
}
