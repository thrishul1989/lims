package com.todaysoft.lims.order.response;

import java.util.Collections;
import java.util.List;

public class OrderSearchResponse<T>
{
    private Integer pageNo;
    
    private Integer pageSize;
    
    private Integer totalCount;
    
    private List<T> records;
    
    public static <T> OrderSearchResponse<T> generate(Integer pageNo, Integer pageSize, int totalCount, List<T> records)
    {
        if (null != pageNo && null != pageSize)
        {
            int minPageNo = 1;
            int maxPageNo = totalCount / pageSize;
            
            if (maxPageNo == 0 || totalCount % pageSize != 0)
            {
                maxPageNo++;
            }
            
            pageNo = pageNo < minPageNo ? minPageNo : pageNo;
            pageNo = pageNo > maxPageNo ? maxPageNo : pageNo;
        }
        
        OrderSearchResponse<T> response = new OrderSearchResponse<T>();
        response.setPageNo(pageNo);
        response.setPageSize(pageSize);
        response.setTotalCount(totalCount);
        response.setRecords(records);
        return response;
    }
    
    public static <T> OrderSearchResponse<T> empty(Integer pageNo, Integer pageSize)
    {
        OrderSearchResponse<T> response = new OrderSearchResponse<T>();
        response.setPageNo(null == pageNo ? null : 1);
        response.setPageSize(pageSize);
        response.setTotalCount(0);
        response.setRecords(Collections.emptyList());
        return response;
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
    
    public Integer getTotalCount()
    {
        return totalCount;
    }
    
    public void setTotalCount(Integer totalCount)
    {
        this.totalCount = totalCount;
    }
    
    public List<T> getRecords()
    {
        return records;
    }
    
    public void setRecords(List<T> records)
    {
        this.records = records;
    }
    
}
