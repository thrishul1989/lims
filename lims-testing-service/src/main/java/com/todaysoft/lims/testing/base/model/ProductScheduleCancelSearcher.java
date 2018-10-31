package com.todaysoft.lims.testing.base.model;

import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.testing.base.entity.ProductCancelRecord;
import com.todaysoft.lims.utils.StringUtils;

public class ProductScheduleCancelSearcher implements ISearcher<ProductCancelRecord>
{
    private String orderCode; //订单编号
    
    private Integer orderType;
    
    private String examineeName;
    
    private String customerName;
    
    private String salesmanName;
    
    private Integer status; //处理状态
    
    private int pageSize;
    
    private int pageNo;
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append(" FROM ProductCancelRecord s where 1=1");
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addFilter(hql, paramNames, parameters);
        hql.append(" order by s.createTime desc ");
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    private void addFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        
        if (StringUtils.isNotEmpty(orderCode))
        {
            hql.append(" AND s.orderCode LIKE:orderCode");
            paramNames.add("orderCode");
            parameters.add("%" + orderCode + "%");
        }
        if (StringUtils.isNotEmpty(examineeName))
        {
            hql.append(" AND s.examineeName LIKE:examineeName");
            paramNames.add("examineeName");
            parameters.add("%" + examineeName + "%");
        }
        
        if (StringUtils.isNotEmpty(orderType))
        {
            hql.append(" AND s.orderType =:orderType");
            paramNames.add("orderType");
            parameters.add(orderType);
        }
        
        if (StringUtils.isNotEmpty(customerName))
        {
            hql.append(" AND s.customerName LIKE:customerName");
            paramNames.add("customerName");
            parameters.add("%" + customerName + "%");
        }
        if (StringUtils.isNotEmpty(salesmanName))
        {
            hql.append(" AND s.salesmanName LIKE:salesmanName");
            paramNames.add("salesmanName");
            parameters.add("%" + salesmanName + "%");
        }
        
        if (StringUtils.isNotEmpty(status))
        {
            hql.append(" AND s.status =:status");
            paramNames.add("status");
            parameters.add(status);
        }
    }
    
    @Override
    public Class<ProductCancelRecord> getEntityClass()
    {
        return ProductCancelRecord.class;
    }
    
    public String getOrderCode()
    {
        return orderCode;
    }
    
    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }
    
    public Integer getOrderType()
    {
        return orderType;
    }
    
    public void setOrderType(Integer orderType)
    {
        this.orderType = orderType;
    }
    
    public String getExamineeName()
    {
        return examineeName;
    }
    
    public void setExamineeName(String examineeName)
    {
        this.examineeName = examineeName;
    }
    
    public String getCustomerName()
    {
        return customerName;
    }
    
    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }
    
    public String getSalesmanName()
    {
        return salesmanName;
    }
    
    public void setSalesmanName(String salesmanName)
    {
        this.salesmanName = salesmanName;
    }
    
    public Integer getStatus()
    {
        return status;
    }
    
    public void setStatus(Integer status)
    {
        this.status = status;
    }
    
    public int getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }
    
    public int getPageNo()
    {
        return pageNo;
    }
    
    public void setPageNo(int pageNo)
    {
        this.pageNo = pageNo;
    }
    
}
