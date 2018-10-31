package com.todaysoft.lims.reagent.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "LIMS_SUPPLIER_REAGENT_KIT")
public class SupplierReagentKit extends AutoKeyEntity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer supplierId;
	private Integer reagentId;
	@Transient
	public Integer getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}
	@Transient
	public Integer getReagentId() {
		return reagentId;
	}
	public void setReagentId(Integer reagentId) {
		this.reagentId = reagentId;
	}

	private Supplier supplier;
	private ReagentKit reagentKit;
	private double price;
	
	@ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="SUPPLIER_ID")
	public Supplier getSupplier() {
		return supplier;
	}
	public void setSupplier(Supplier supplier) {
		this.supplier = supplier;
	}
	
	@ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name="REAGENT_KIT_ID")
	public ReagentKit getReagentKit() {
		return reagentKit;
	}
	public void setReagentKit(ReagentKit reagentKit) {
		this.reagentKit = reagentKit;
	}
	
	@Column(name = "PRICE")
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}

}
