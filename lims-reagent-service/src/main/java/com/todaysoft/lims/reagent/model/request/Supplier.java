package com.todaysoft.lims.reagent.model.request;

import java.util.List;

import com.todaysoft.lims.reagent.entity.SupplierContacter;





public class Supplier {
	
	private Integer id;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	private String code;
	
	
	public List<SupplierContacter> getSupplierContacterList() {
		return supplierContacterList;
	}
	public void setSupplierContacterList(
			List<SupplierContacter> supplierContacterList) {
		this.supplierContacterList = supplierContacterList;
	}
	private String name;
	private  String address;
	private List<SupplierContacter> supplierContacterList;
	private Integer pageSize;
	private Integer pageNo;
	
	
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
	

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

}
