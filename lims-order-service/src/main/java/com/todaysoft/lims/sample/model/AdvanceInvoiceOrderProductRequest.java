package com.todaysoft.lims.sample.model;

import java.util.List;

public class AdvanceInvoiceOrderProductRequest
{
    private String id;
    
    private String updaterId;
    
    private String updaterName;
    
    private String requestParams;//请求体参数
    
    private List<DefaultInvoiceOrderProductRequest> orderProduct;
    
    public List<DefaultInvoiceOrderProductRequest> getOrderProduct()
    {
        return orderProduct;
    }
    
    public void setOrderProduct(List<DefaultInvoiceOrderProductRequest> orderProduct)
    {
        this.orderProduct = orderProduct;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getUpdaterId()
    {
        return updaterId;
    }
    
    public void setUpdaterId(String updaterId)
    {
        this.updaterId = updaterId;
    }
    
    public String getUpdaterName()
    {
        return updaterName;
    }
    
    public void setUpdaterName(String updaterName)
    {
        this.updaterName = updaterName;
    }
    
    public String getRequestParams()
    {
        return requestParams;
    }
    
    public void setRequestParams(String requestParams)
    {
        this.requestParams = requestParams;
    }
    
}
