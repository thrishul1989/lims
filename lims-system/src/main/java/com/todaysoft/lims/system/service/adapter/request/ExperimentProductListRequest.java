package com.todaysoft.lims.system.service.adapter.request;

import com.todaysoft.lims.system.model.vo.StoreContainer;

public class ExperimentProductListRequest {

	private String code;
	private String name;
	private StoreContainer storeContainer;
	private String unit;//单位
	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public StoreContainer getStoreContainer() {
		return storeContainer;
	}

	public void setStoreContainer(StoreContainer storeContainer) {
		this.storeContainer = storeContainer;
	}



}
