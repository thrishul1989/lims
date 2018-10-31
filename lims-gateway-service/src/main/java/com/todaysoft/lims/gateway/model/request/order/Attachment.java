package com.todaysoft.lims.gateway.model.request.order;

import com.todaysoft.lims.persist.UuidKeyEntity;

/**
 * 订单受检人表
 * @author admin
 *
 */

public class Attachment extends UuidKeyEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String suffix;//附件后缀
	private String storageType;//存储方式：1-内部存储 2-阿里OSS
	private String storageKey; //外部存储对应主键
	private String innerUrl; //内部地址
	
	public Attachment() {
		super();
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getStorageType() {
		return storageType;
	}
	public void setStorageType(String storageType) {
		this.storageType = storageType;
	}

	public String getStorageKey() {
		return storageKey;
	}
	public void setStorageKey(String storageKey) {
		this.storageKey = storageKey;
	}

	public String getInnerUrl() {
		return innerUrl;
	}
	public void setInnerUrl(String innerUrl) {
		this.innerUrl = innerUrl;
	}
	
	
	
}
