package com.todaysoft.lims.gateway.model;

import com.todaysoft.lims.gateway.model.request.disease.Disease;



public class ProductDisease {
	
	private String id;
	private Product product;
	private Disease disease;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Disease getDisease() {
		return disease;
	}
	public void setDisease(Disease disease) {
		this.disease = disease;
	}
}
