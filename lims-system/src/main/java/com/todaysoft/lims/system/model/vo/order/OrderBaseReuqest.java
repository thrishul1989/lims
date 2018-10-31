package com.todaysoft.lims.system.model.vo.order;

import com.todaysoft.lims.base.RecordFilter;
import com.todaysoft.lims.order.request.OrderColumnFilter;
import com.todaysoft.lims.order.request.OrderTableFilter;

public class OrderBaseReuqest
{
    private OrderTableFilter tableFilter; //是否关联延迟  --输入参数
    
    private OrderColumnFilter columnFilter; //是否关联延迟 --输出参数
    
    private String orderByClause;
    
    private RecordFilter filter;
    
    public OrderTableFilter getTableFilter()
    {
        return tableFilter;
    }
    
    public void setTableFilter(OrderTableFilter tableFilter)
    {
        this.tableFilter = tableFilter;
    }
    
    public OrderColumnFilter getColumnFilter()
    {
        return columnFilter;
    }
    
    public void setColumnFilter(OrderColumnFilter columnFilter)
    {
        this.columnFilter = columnFilter;
    }
    
    public String getOrderByClause()
    {
        return orderByClause;
    }
    
    public void setOrderByClause(String orderByClause)
    {
        this.orderByClause = orderByClause;
    }
    
    public RecordFilter getFilter()
    {
        return filter;
    }
    
    public void setFilter(RecordFilter filter)
    {
        this.filter = filter;
    }
    
}
