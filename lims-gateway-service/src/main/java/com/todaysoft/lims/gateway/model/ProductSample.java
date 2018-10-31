package com.todaysoft.lims.gateway.model;



import com.fasterxml.jackson.annotation.JsonIgnore;


public class ProductSample {

	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	private Product product;
	private Sample  sample;

	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	
	public Sample getSample() {
		return sample;
	}
	public void setSample(Sample sample) {
		this.sample = sample;
	}
}
