package com.todaysoft.lims.product.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;
import com.todaysoft.lims.product.entity.disease.Disease;


@Entity
@Table(name = "LIMS_PRODUCT_DISEASE")
public class ProductDisease extends UuidKeyEntity{

	
	private Product product;
	private Disease disease;

	
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
	@JoinColumn(name = "DISEASE_ID")
	@NotFound(action=NotFoundAction.IGNORE)
	
	public Disease getDisease() {
		return disease;
	}
	public void setDisease(Disease disease) {
		this.disease = disease;
	}
}
