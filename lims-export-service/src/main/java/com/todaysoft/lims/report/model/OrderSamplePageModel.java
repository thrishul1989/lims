package com.todaysoft.lims.report.model;


import com.todaysoft.lims.persist.Pagination;
import com.todaysoft.lims.report.entity.ReportOrderSampleInfo;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

public class OrderSamplePageModel<E>{

    private int pageNo;

    private int pageSize;

    private int totalCount;

    private List<String[]> records;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<String[]> getRecords() {
        return records;
    }

    public void setRecords(List<String[]> records) {
        this.records = records;
    }

    public OrderSamplePageModel()
    {

    }

    public OrderSamplePageModel(Pagination<E> pagination, List<String[]> records)
    {
        if(null != pagination)
        {
            this.pageNo = pagination.getPageNo();
            this.pageSize = pagination.getPageSize();
            this.totalCount = pagination.getTotalCount();
            this.records = records;
        }
    }
}
