package com.todaysoft.lims.system.model.vo;

public class SupplierReagent {
	
	private Integer supplierId;
	private Integer reagentId;
	
	public Integer getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}
	public Integer getReagentId() {
		return reagentId;
	}
	public void setReagentId(Integer reagentId) {
		this.reagentId = reagentId;
	}
	private Integer id;
	private Supplier supplier;
	private Reagent reagent;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Supplier getSupplier() {
		return supplier;
	}
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	public Reagent getReagent() {
		return reagent;
	}
	public void setReagent(Reagent reagent) {
		this.reagent = reagent;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	private double price;
	private Integer pageSize;
	private Integer pageNo;
	private Integer sort;
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
}
