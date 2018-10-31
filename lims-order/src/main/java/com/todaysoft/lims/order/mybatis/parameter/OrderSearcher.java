package com.todaysoft.lims.order.mybatis.parameter;

import com.todaysoft.lims.order.request.OrderColumnFilter;
import com.todaysoft.lims.order.request.OrderTableFilter;

public class OrderSearcher extends BaseOrderSearcher
{
    private String productCode;
    
    private String sampleCode;
    
    private OrderTableFilter tableFilter;
    
    private OrderColumnFilter columnFilter;
    
    private String orderByClause;
    
    private Integer notTestingStatus;
    
    private String customerName;

    private String prjManagerName;

    private String projectManager;

    public String getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }

    public String getPrjManagerName() {
        return prjManagerName;
    }

    public void setPrjManagerName(String prjManagerName) {
        this.prjManagerName = prjManagerName;
    }

    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public String getProductCode()
    {
        return productCode;
    }
    
    public void setProductCode(String productCode)
    {
        this.productCode = productCode;
    }
    
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
    
    public Integer getNotTestingStatus()
    {
        return notTestingStatus;
    }
    
    public void setNotTestingStatus(Integer notTestingStatus)
    {
        this.notTestingStatus = notTestingStatus;
    }
    
    @Override
    public String getCustomerName()
    {
        return customerName;
    }
    
    @Override
    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }
}
