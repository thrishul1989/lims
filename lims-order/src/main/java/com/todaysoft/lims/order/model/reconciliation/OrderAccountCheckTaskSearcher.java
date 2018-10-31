package com.todaysoft.lims.order.model.reconciliation;

import com.todaysoft.lims.order.mybatis.model.OrderAccountCheckTask;

public class OrderAccountCheckTaskSearcher extends OrderAccountCheckTask
{
    private int pageNo;
    
    private int pageSize;
    
    private int offset;
    
    private int limit;
    
    public int getPageNo()
    {
        return pageNo;
    }
    
    public void setPageNo(int pageNo)
    {
        this.pageNo = pageNo;
    }
    
    public int getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }
    
    public int getOffset()
    {
        return offset;
    }
    
    public void setOffset(int offset)
    {
        this.offset = offset;
    }
    
    public int getLimit()
    {
        return limit;
    }
    
    public void setLimit(int limit)
    {
        this.limit = limit;
    }
    
}
