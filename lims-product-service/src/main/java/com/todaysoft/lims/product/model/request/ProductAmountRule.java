package com.todaysoft.lims.product.model.request;

import java.math.BigDecimal;

public class ProductAmountRule
{
    private Integer count;
    
    private BigDecimal amount;
    
    public Integer getCount()
    {
        return count;
    }
    
    public void setCount(Integer count)
    {
        this.count = count;
    }
    
    public BigDecimal getAmount()
    {
        return amount;
    }
    
    public void setAmount(BigDecimal amount)
    {
        this.amount = amount;
    }
}
