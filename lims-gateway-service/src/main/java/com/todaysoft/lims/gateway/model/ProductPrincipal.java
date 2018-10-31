package com.todaysoft.lims.gateway.model;

public class ProductPrincipal {
	private String id;
	private Product product;
	private User principal;

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
	public User getPrincipal() {
		return principal;
	}
	public void setPrincipal(User principal) {
		this.principal = principal;
	}
}
