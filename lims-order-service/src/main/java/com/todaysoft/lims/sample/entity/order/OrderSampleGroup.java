package com.todaysoft.lims.sample.entity.order;

import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.persist.UuidKeyEntity;

/**
 * 订单-样本组
 * @author admin
 *
 */

public class OrderSampleGroup extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private String name;
    
    private List<OrderResearchSample> orderResearchSample = new ArrayList<OrderResearchSample>();
    
    private Order order;
    
    public OrderSampleGroup()
    {
        super();
    }
    
    public OrderSampleGroup(String name)
    {
        super();
        this.name = name;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public List<OrderResearchSample> getOrderResearchSample()
    {
        return orderResearchSample;
    }
    
    public void setOrderResearchSample(List<OrderResearchSample> orderResearchSample)
    {
        this.orderResearchSample = orderResearchSample;
    }
    
    public Order getOrder()
    {
        return order;
    }
    
    public void setOrder(Order order)
    {
        this.order = order;
    }
    
}
