package com.todaysoft.lims.sample.model;

import java.math.BigDecimal;

public class DefaultInvoiceOrderProductRequest
{
    private String orderProductId;
    
    private BigDecimal orderProductPrice;
    
    public String getOrderProductId()
    {
        return orderProductId;
    }
    
    public void setOrderProductId(String orderProductId)
    {
        this.orderProductId = orderProductId;
    }
    
    public BigDecimal getOrderProductPrice()
    {
        return orderProductPrice;
    }
    
    public void setOrderProductPrice(BigDecimal orderProductPrice)
    {
        this.orderProductPrice = orderProductPrice;
    }
    
}
