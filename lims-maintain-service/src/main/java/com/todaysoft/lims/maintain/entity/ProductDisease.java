package com.todaysoft.lims.maintain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_PRODUCT_DISEASE")
public class ProductDisease extends UuidKeyEntity
{
    private static final long serialVersionUID = 1820408717350175158L;
    
    private String productId;
    
    private String diseaseId;
    
    @Column(name = "PRODUCT_ID")
    public String getProductId()
    {
        return productId;
    }
    
    public void setProductId(String productId)
    {
        this.productId = productId;
    }
    
    @Column(name = "DISEASE_ID")
    public String getDiseaseId()
    {
        return diseaseId;
    }
    
    public void setDiseaseId(String diseaseId)
    {
        this.diseaseId = diseaseId;
    }
}
