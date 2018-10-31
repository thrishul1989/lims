package com.todaysoft.lims.testing.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

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
    
    private Product product;
    
    private String productName;
    
    private Integer productPrice;
    
    private Integer productStatus;//产品状态，0：待送测；1：已完成 2:已取消；
    
    private String dataUrl;//数据链接地址
    
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
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID")
    @NotFound(action = NotFoundAction.IGNORE)
    public Product getProduct()
    {
        return product;
    }
    
    public void setProduct(Product product)
    {
        this.product = product;
    }
    
    @Column(name = "PRODUCT_NAME")
    public String getProductName()
    {
        return productName;
    }
    
    public void setProductName(String productName)
    {
        this.productName = productName;
    }
    
    @Column(name = "PRODUCT_PRICE")
    public Integer getProductPrice()
    {
        return productPrice;
    }
    
    public void setProductPrice(Integer productPrice)
    {
        this.productPrice = productPrice;
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
    
    @Column(name = "DATA_URL")
    public String getDataUrl()
    {
        return dataUrl;
    }
    
    public void setDataUrl(String dataUrl)
    {
        this.dataUrl = dataUrl;
    }
    
}
