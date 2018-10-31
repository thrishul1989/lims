package com.todaysoft.lims.system.modules.bmm.model.request;

import java.util.List;

public class AdvanceInvoiceModelRequest
{
    private String orderId;
    
    private List<DefaultInvoiceOrderProductRequest> orderProducts;
    
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
    
    public List<DefaultInvoiceOrderProductRequest> getOrderProducts()
    {
        return orderProducts;
    }
    
    public void setOrderProducts(List<DefaultInvoiceOrderProductRequest> orderProducts)
    {
        this.orderProducts = orderProducts;
    }
    
}
