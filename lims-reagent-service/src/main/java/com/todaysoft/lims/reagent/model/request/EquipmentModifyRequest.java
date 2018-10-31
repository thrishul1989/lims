package com.todaysoft.lims.reagent.model.request;

import java.util.Date;

import com.todaysoft.lims.reagent.entity.enums.EquipmentStatus;

public class EquipmentModifyRequest {
	private String id;
	private String equipmentNo; // 设备编号
	private String name; // 设备名称
	private String serialNo; // 机身编号
	private String specificationNo; // 规格编号
	private Double price; // 价格
	private String vendor; // 生产厂商
	private Date acceptDate; // 接受日期
	private Date useDate; // 使用日期
	private String purpose; // 用途
	private String principal; // 设备负责人
	private String status; // 状态
	private String serviceMan; // 维修联系人
	private String servicePhone; // 维修联系电话

	private String position; //摆放位置
	private String remark; //备注
	
	
	
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getSpecificationNo() {
		return specificationNo;
	}

	public void setSpecificationNo(String specificationNo) {
		this.specificationNo = specificationNo;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public Date getAcceptDate() {
		return acceptDate;
	}

	public void setAcceptDate(Date acceptDate) {
		this.acceptDate = acceptDate;
	}

	public Date getUseDate() {
		return useDate;
	}

	public void setUseDate(Date useDate) {
		this.useDate = useDate;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getServiceMan() {
		return serviceMan;
	}

	public void setServiceMan(String serviceMan) {
		this.serviceMan = serviceMan;
	}

	public String getServicePhone() {
		return servicePhone;
	}

	public void setServicePhone(String servicePhone) {
		this.servicePhone = servicePhone;
	}
}
