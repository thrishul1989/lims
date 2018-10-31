package com.todaysoft.lims.sample.dao.searcher;

import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.sample.entity.order.Order;
import com.todaysoft.lims.utils.StringUtils;

public class OrderViewSearcher implements ISearcher<Order>
{
    
    private String sampleCode;
    
    private String orderExaminee;
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        if (StringUtils.isNotEmpty(orderExaminee))
        {
            hql.append("select s FROM Order s left join s.orderExamineeList as e  WHERE s.deleted =false  ");
        }
        else
        {
            hql.append("select DISTINCT o FROM Order o,OrderSampleView osv WHERE osv.orderId = o.id and o.deleted = false");
        }
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addFilter(hql, paramNames, parameters);
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    private void addFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (StringUtils.isNotEmpty(sampleCode))
        {
            hql.append(" AND osv.sampleCode LIKE :sampleCode");
            paramNames.add("sampleCode");
            parameters.add("%" + sampleCode + "%");
        }
        
        if (StringUtils.isNotEmpty(orderExaminee))
        {
            hql.append(" AND e.name LIKE :orderExaminee");
            paramNames.add("orderExaminee");
            parameters.add("%" + orderExaminee + "%");
        }
    }
    
    @Override
    public Class<Order> getEntityClass()
    {
        return Order.class;
    }
    
    public String getOrderExaminee()
    {
        return orderExaminee;
    }
    
    public void setOrderExaminee(String orderExaminee)
    {
        this.orderExaminee = orderExaminee;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
}
