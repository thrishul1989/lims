package com.todaysoft.lims.sample.model.order;

import java.math.BigDecimal;
import java.util.List;

import com.todaysoft.lims.sample.model.DefaultInvoiceOrderProductRequest;

public class DefaultInvoiceRequest
{
    private String id;
    
    private String orderId;
    
    private BigDecimal amount;//订单金额，单位（分）
    
    private BigDecimal subsidiarySampleAmount; //附属样本额外收费金额，单位（分）
    
    private String updaterId;
    
    private String updaterName;
    
    private List<DefaultInvoiceOrderProductRequest> orderProduct;
    
    public List<DefaultInvoiceOrderProductRequest> getOrderProduct()
    {
        return orderProduct;
    }
    
    public void setOrderProduct(List<DefaultInvoiceOrderProductRequest> orderProduct)
    {
        this.orderProduct = orderProduct;
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
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
    
    public BigDecimal getAmount()
    {
        return amount;
    }
    
    public void setAmount(BigDecimal amount)
    {
        this.amount = amount;
    }
    
    public BigDecimal getSubsidiarySampleAmount()
    {
        return subsidiarySampleAmount;
    }
    
    public void setSubsidiarySampleAmount(BigDecimal subsidiarySampleAmount)
    {
        this.subsidiarySampleAmount = subsidiarySampleAmount;
    }
    
}
