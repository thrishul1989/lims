package com.todaysoft.lims.maintain.order.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;

/**
 * 订单-主样本-产品
 * @author admin
 *
 */

@Entity
@Table(name = "LIMS_ORDER_RESEARCH_PRODUCT")
public class OrderResearchProduct extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private OrderResearchSample orderResearchSample;
    
    private Integer productStatus;//产品状态，0：待送测；1：已完成 2:已取消；
    
    public OrderResearchProduct()
    {
        super();
    }
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORS_ID")
    @JsonIgnore
    public OrderResearchSample getOrderResearchSample()
    {
        return orderResearchSample;
    }
    
    public void setOrderResearchSample(OrderResearchSample orderResearchSample)
    {
        this.orderResearchSample = orderResearchSample;
    }
    
    @Column(name = "PRODUCT_STATUS")
    public Integer getProductStatus()
    {
        return productStatus;
    }
    
    public void setProductStatus(Integer productStatus)
    {
        this.productStatus = productStatus;
    }
    
}
