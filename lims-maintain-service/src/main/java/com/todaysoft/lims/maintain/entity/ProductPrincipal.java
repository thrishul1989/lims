package com.todaysoft.lims.maintain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "LIMS_PRODUCT_PRINCIPAL")
public class ProductPrincipal implements Serializable
{
    private static final long serialVersionUID = -2674850446907905007L;
    
    private String id;
    
    private Product product;
    
    private User principal;
    
    @Id
    @Column(name = "ID")
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    @JsonIgnore
    public Product getProduct()
    {
        return product;
    }
    
    public void setProduct(Product product)
    {
        this.product = product;
    }
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "PRINCIPAL_ID")
    public User getPrincipal()
    {
        return principal;
    }
    
    public void setPrincipal(User principal)
    {
        this.principal = principal;
    }
}
