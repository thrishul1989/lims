package com.todaysoft.lims.order.request;

public class SampleSynchronizeRequest
{
    private String customerId;//客户id
    
    private String scheduleName;//流程节点
    
    private Integer pageNo;
    
    private Integer pageSize;
    
    private Integer offset;
    
    private Integer limit;
    
    private String orderId;
    
    public String getScheduleName()
    {
        return scheduleName;
    }
    
    public void setScheduleName(String scheduleName)
    {
        this.scheduleName = scheduleName;
    }
    
    public Integer getPageNo()
    {
        return pageNo;
    }
    
    public void setPageNo(Integer pageNo)
    {
        this.pageNo = pageNo;
    }
    
    public Integer getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(Integer pageSize)
    {
        this.pageSize = pageSize;
    }
    
    public Integer getOffset()
    {
        return offset;
    }
    
    public void setOffset(Integer offset)
    {
        this.offset = offset;
    }
    
    public Integer getLimit()
    {
        return limit;
    }
    
    public void setLimit(Integer limit)
    {
        this.limit = limit;
    }
    
    public String getCustomerId()
    {
        return customerId;
    }
    
    public void setCustomerId(String customerId)
    {
        this.customerId = customerId;
    }
    
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
    
}
