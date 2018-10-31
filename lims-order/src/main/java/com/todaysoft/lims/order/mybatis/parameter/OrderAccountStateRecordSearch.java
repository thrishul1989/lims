package com.todaysoft.lims.order.mybatis.parameter;

import com.todaysoft.lims.order.mybatis.model.OrderAccountStateRecord;

public class OrderAccountStateRecordSearch extends OrderAccountStateRecord
{
    private Integer offset;
    
    private Integer limit;
    
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
    
}
