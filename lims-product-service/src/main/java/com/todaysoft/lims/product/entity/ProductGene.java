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
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;
import com.todaysoft.lims.product.entity.disease.Disease;
import com.todaysoft.lims.product.entity.disease.Gene;
import com.todaysoft.lims.product.model.response.GenePageModel;
import com.todaysoft.lims.product.model.response.SimpleGene;
@Entity
@Table(name = "LIMS_PRODUCT_GENE")
public class ProductGene extends UuidKeyEntity{
	
	
	private Product product;
	private Gene gene;

	
	
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
	@JoinColumn(name = "GENE_ID")
	@NotFound(action=NotFoundAction.IGNORE)
	
	public Gene getGene() {
		return gene;
	}
	public void setGene(Gene gene) {
		this.gene = gene;
	}
	
}
