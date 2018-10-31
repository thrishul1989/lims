package com.todaysoft.lims.system.model.vo;


public class StorageLocation  {
	private String id;
	private Integer storageId; //关联存储容器
	private String locationCode; //存储位置编号
	private String sampleType; //存储物类型
	private String sampleKey; //存储物ID
	private String storageCapacity; //存储量
	/*private String state; //是否已用
*/	private String name;
	
	
	private String deviceId; //关联存储容器
	private String code; //存储位置编号
	private String sampleId; //存储物类型
	private Integer state; //是否已用
	
	
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStorageId() {
		return storageId;
	}
	public void setStorageId(Integer storageId) {
		this.storageId = storageId;
	}
	public String getLocationCode() {
		return locationCode;
	}
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}
	public String getSampleType() {
		return sampleType;
	}
	public void setSampleType(String sampleType) {
		this.sampleType = sampleType;
	}
	public String getSampleKey() {
		return sampleKey;
	}
	public void setSampleKey(String sampleKey) {
		this.sampleKey = sampleKey;
	}
	public String getStorageCapacity() {
		return storageCapacity;
	}
	public void setStorageCapacity(String storageCapacity) {
		this.storageCapacity = storageCapacity;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getSampleId() {
		return sampleId;
	}
	public void setSampleId(String sampleId) {
		this.sampleId = sampleId;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
	
	
	
}
