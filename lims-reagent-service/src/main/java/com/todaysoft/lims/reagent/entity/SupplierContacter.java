package com.todaysoft.lims.reagent.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "LIMS_SUPPLIER_CONTACTER")
public class SupplierContacter extends AutoKeyEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8574755270908947060L;
	private String name;
	private String mobile;
	private String duty;
	private Supplier supplier;
	
	@Column(name = "NAME")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "MOBILE")
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	@Column(name = "DUTY")
	public String getDuty() {
		return duty;
	}
	public void setDuty(String duty) {
		this.duty = duty;
	}
	
	@ManyToOne(fetch= FetchType.EAGER,targetEntity=Supplier.class)
    @JoinColumn(name="SUPPLIER_ID")
	@JsonIgnore
	public Supplier getSupplier() {
		return supplier;
	}
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}

}
