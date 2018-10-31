package com.todaysoft.lims.technical.model.request;

import com.todaysoft.lims.technical.model.VariableModel;

public class ReportEvent
{
    private VariableModel model;
    
    private String orderId;
    
    private String productId;
    
    private String tag;
    
    public String getTag()
    {
        return tag;
    }
    
    public void setTag(String tag)
    {
        this.tag = tag;
    }
    
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
    
    public String getProductId()
    {
        return productId;
    }
    
    public void setProductId(String productId)
    {
        this.productId = productId;
    }
    
    public VariableModel getModel()
    {
        return model;
    }
    
    public void setModel(VariableModel model)
    {
        this.model = model;
    }
}
