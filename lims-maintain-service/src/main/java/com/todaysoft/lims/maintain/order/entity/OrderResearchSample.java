package com.todaysoft.lims.maintain.order.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.lims.persist.UuidKeyEntity;

@Entity
@Table(name = "LIMS_ORDER_RESEARCH_SAMPLE")
public class OrderResearchSample extends UuidKeyEntity
{
    private static final long serialVersionUID = 1L;
    
    private OrderEntity order; //订单对象
    
    private String sampleCode; //样本编号
    
    private List<OrderResearchProduct> orderResearchProduct = new ArrayList<OrderResearchProduct>();
    
    public OrderResearchSample()
    {
        super();
    }
    
    @Column(name = "SAMPLE_CODE")
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = StringUtils.isNotEmpty(sampleCode) ? sampleCode.toUpperCase() : sampleCode;
    }
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ORDER_ID")
    @JsonIgnore
    public OrderEntity getOrder()
    {
        return order;
    }
    
    public void setOrder(OrderEntity order)
    {
        this.order = order;
    }
    
    @OneToMany(mappedBy = "orderResearchSample", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    public List<OrderResearchProduct> getOrderResearchProduct()
    {
        return orderResearchProduct;
    }
    
    public void setOrderResearchProduct(List<OrderResearchProduct> orderResearchProduct)
    {
        this.orderResearchProduct = orderResearchProduct;
    }
}
