package com.todaysoft.lims.system.model.vo;

public class OrderSample
{
    
    private int orderType;
    
    private Integer pageSize;
    
    private Integer pageNo;
    
    public Integer getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(Integer pageSize)
    {
        this.pageSize = pageSize;
    }
    
    public Integer getPageNo()
    {
        return pageNo;
    }
    
    public void setPageNo(Integer pageNo)
    {
        this.pageNo = pageNo;
    }
    
    public int getOrderType()
    {
        return orderType;
    }
    
    public void setOrderType(int orderType)
    {
        this.orderType = orderType;
    }
    
    private Integer freeCount;
    
    private Integer extraMoney;
    
    public Integer getFreeCount()
    {
        return freeCount;
    }
    
    public void setFreeCount(Integer freeCount)
    {
        this.freeCount = freeCount;
    }
    
    public Integer getExtraMoney()
    {
        return extraMoney;
    }
    
    public void setExtraMoney(Integer extraMoney)
    {
        this.extraMoney = extraMoney;
    }
    
}
