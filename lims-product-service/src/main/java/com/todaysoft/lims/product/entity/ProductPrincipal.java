package com.todaysoft.lims.product.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_PRODUCT_PRINCIPAL")
public class ProductPrincipal extends UuidKeyEntity {
	
	private Product product;
	private User principal;

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCT_ID")
	@JsonIgnore
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "PRINCIPAL_ID")
	public User getPrincipal() {
		return principal;
	}
	public void setPrincipal(User principal) {
		this.principal = principal;
	}
	
}
