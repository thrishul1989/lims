package com.todaysoft.lims.system.model.searcher;

import java.util.Date;

import com.todaysoft.lims.system.model.vo.ExperEquipment;

public class ExperEquipmentSearcher  extends ExperEquipment{
	
	
	private int pageNo;
	private int pageSize;
	private String equipmentNo; // 设备编号
	private String name; // 设备名称
	private String vendor; // 生产厂商
	private Date acceptDateStart; // 接受日期
	private Date acceptDateEnd;
	private String principal; // 设备负责人
	
	public String getEquipmentNo() {
		return equipmentNo;
	}
	public void setEquipmentNo(String equipmentNo) {
		this.equipmentNo = equipmentNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVendor() {
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	public Date getAcceptDateStart() {
		return acceptDateStart;
	}
	public void setAcceptDateStart(Date acceptDateStart) {
		this.acceptDateStart = acceptDateStart;
	}
	public Date getAcceptDateEnd() {
		return acceptDateEnd;
	}
	public void setAcceptDateEnd(Date acceptDateEnd) {
		this.acceptDateEnd = acceptDateEnd;
	}
	public String getPrincipal() {
		return principal;
	}
	public void setPrincipal(String principal) {
		this.principal = principal;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	

}
