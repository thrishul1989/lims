package com.todaysoft.lims.gateway.model.request.order;

import com.todaysoft.lims.gateway.model.Product;
import com.todaysoft.lims.persist.UuidKeyEntity;

/**
 * 订单检测表
 * @author admin
 *
 */

public class OrderProduct extends UuidKeyEntity
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private Order order; //订单对象
    
    private Product product;//关联产品
    
    private String productId;
    
    public String getProductId()
    {
        return productId;
    }
    
    public void setProductId(String productId)
    {
        this.productId = productId;
    }
    
    public Order getOrder()
    {
        return order;
    }
    
    public void setOrder(Order order)
    {
        this.order = order;
    }
    
    public Product getProduct()
    {
        return product;
    }
    
    public void setProduct(Product product)
    {
        this.product = product;
    }
    
}
