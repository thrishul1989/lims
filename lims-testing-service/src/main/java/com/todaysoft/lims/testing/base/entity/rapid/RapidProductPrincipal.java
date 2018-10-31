package com.todaysoft.lims.testing.base.entity.rapid;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "LIMS_PRODUCT_PRINCIPAL")
public class RapidProductPrincipal implements Serializable
{
    private static final long serialVersionUID = -2674850446907905007L;
    
    private String id;
    
    private String productId;
    
    private String principalId;
    
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
    
    @Column(name = "PRODUCT_ID")
    public String getProductId()
    {
        return productId;
    }
    
    public void setProductId(String productId)
    {
        this.productId = productId;
    }
    
    @Column(name = "PRINCIPAL_ID")
    public String getPrincipalId()
    {
        return principalId;
    }
    
    public void setPrincipalId(String principalId)
    {
        this.principalId = principalId;
    }
}
