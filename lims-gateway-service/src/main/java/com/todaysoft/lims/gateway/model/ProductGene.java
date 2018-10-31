package com.todaysoft.lims.gateway.model;

import com.todaysoft.lims.gateway.model.request.disease.Gene;



public class ProductGene {
	private String id;
	private Product product;
	private Gene gene;
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
	public Gene getGene() {
		return gene;
	}
	public void setGene(Gene gene) {
		this.gene = gene;
	}
}
