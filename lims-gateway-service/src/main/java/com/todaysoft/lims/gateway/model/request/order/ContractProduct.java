package com.todaysoft.lims.gateway.model.request.order;


import com.todaysoft.lims.persist.UuidKeyEntity;

public class ContractProduct extends UuidKeyEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String contract;
	private String product;
	
	
	public String getContract() {
		return contract;
	}
	public void setContract(String contract) {
		this.contract = contract;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	
	
	
	

}
