package com.todaysoft.lims.schedule.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_ORDER_RESEARCH_PRODUCT")
public class ResearchOrderProduct extends UuidKeyEntity
{
    private static final long serialVersionUID = 6601685324052139100L;
    
    private String productId;
    
    private OrderResearchSample sample;
    
    @Column(name = "PRODUCT_ID")
    public String getProductId()
    {
        return productId;
    }
    
    public void setProductId(String productId)
    {
        this.productId = productId;
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORS_ID")
    public OrderResearchSample getSample()
    {
        return sample;
    }
    
    public void setSample(OrderResearchSample sample)
    {
        this.sample = sample;
    }
}
