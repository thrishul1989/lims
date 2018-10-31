package com.todaysoft.lims.technical.utils;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.pagehelper.PageInfo;

public class Pagination<T>
{
    private int pageNo;
    
    private int pageSize = 10;
    
    private int totalCount;
    
    private List<T> records;
    
    public Pagination()
    {
    }
    
    public Pagination(Pagination<T> pagination)
    {
        this.pageNo = pagination.getPageNo();
        this.pageSize = pagination.getPageSize();
        this.totalCount = pagination.getTotalCount();
    }
    
    public Pagination(PageInfo<T> pagination)
    {
        records = pagination.getList();
        pageNo = pagination.getPageNum();
        totalCount = (int)pagination.getTotal();
    }
    
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
    
    public int getTotalCount()
    {
        return totalCount;
    }
    
    public void setTotalCount(int totalCount)
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
    
    @JsonIgnore
    public int getTotalPage()
    {
        int pageCount = totalCount / pageSize;
        
        if (pageCount == 0 || totalCount % pageSize != 0)
        {
            pageCount++;
        }
        
        return pageCount;
    }
    
    @JsonIgnore
    public boolean isLastPage()
    {
        return pageNo >= getTotalPage();
    }
}
