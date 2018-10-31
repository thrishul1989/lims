package com.todaysoft.lims.schedule.model;

import java.util.Date;
import java.util.List;

public class OrderTestings
{
    private String id;
    
    private Date submitTime;
    
    private List<OrderProductTestings> productTestings;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public Date getSubmitTime()
    {
        return submitTime;
    }
    
    public void setSubmitTime(Date submitTime)
    {
        this.submitTime = submitTime;
    }
    
    public List<OrderProductTestings> getProductTestings()
    {
        return productTestings;
    }
    
    public void setProductTestings(List<OrderProductTestings> productTestings)
    {
        this.productTestings = productTestings;
    }
}
