package com.todaysoft.lims.system.model.vo;


public class CheckManagementCheck {
	
	private CheckManagement checkManagement;// 检测管理
	
	private Integer checkId;// 检测ID
	
	private Integer checkOrder;// 检测顺序
	
	
	
	public CheckManagement getCheckManagement() {
		return checkManagement;
	}
	public void setCheckManagement(CheckManagement checkManagement) {
		this.checkManagement = checkManagement;
	}
	public Integer getCheckId() {
		return checkId;
	}
	public void setCheckId(Integer checkId) {
		this.checkId = checkId;
	}
	public Integer getCheckOrder() {
		return checkOrder;
	}
	public void setCheckOrder(Integer checkOrder) {
		this.checkOrder = checkOrder;
	}
	
}
