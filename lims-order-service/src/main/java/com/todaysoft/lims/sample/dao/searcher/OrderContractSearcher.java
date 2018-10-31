package com.todaysoft.lims.sample.dao.searcher;

import java.util.ArrayList;
import java.util.List;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.sample.entity.order.Order;
import com.todaysoft.lims.utils.StringUtils;

public class OrderContractSearcher implements ISearcher<Order>
{
    private String contractId;
    
    private String orderCode;
    
    private Integer marketingCenter;
    
    private String custormer;
    
    private String signUser;
    
    private Integer testingStatus; //订单主状态
    
    private Integer paymentStatus; //支付状态
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        hql.append("SELECT o FROM Order o left join o.contract as c  WHERE o.deleted = false and o.testingStatus != 0 "); /*状态变更 ：加上已取消*/
        List<String> paramNames = new ArrayList<String>();
        List<Object> parameters = new ArrayList<Object>();
        addTestingStatusFilter(hql, paramNames, parameters);
        addPaymentStatusFilter(hql, paramNames, parameters);
        addContractIdFilter(hql, paramNames, parameters);
        addSignUserFilter(hql, paramNames, parameters);
        addOrderCodeFilter(hql, paramNames, parameters);
        addMarketingCenterFilter(hql, paramNames, parameters);
        //addCustormerFilter(hql, paramNames, parameters);
        hql.append(" order by o.createTime desc");
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(paramNames);
        queryer.setValues(parameters);
        return queryer;
    }
    
    private void addTestingStatusFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (StringUtils.isNotEmpty(testingStatus))
        {
            hql.append(" AND o.testingStatus =:testingStatus");
            paramNames.add("testingStatus");
            parameters.add(testingStatus);
        }
        
    }
    
    private void addPaymentStatusFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        
        if (StringUtils.isNotEmpty(paymentStatus))
        {
            hql.append(" AND o.paymentStatus =:paymentStatus");
            paramNames.add("paymentStatus");
            parameters.add(paymentStatus);
        }
    }
    
    private void addContractIdFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (StringUtils.isNotEmpty(contractId))
        {
            hql.append(" AND c.id = :contractId");
            paramNames.add("contractId");
            parameters.add(contractId);
            
        }
    }
    
    private void addSignUserFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (StringUtils.isNotEmpty(signUser))
        {
            hql.append(" AND c.signUser like :signUser");
            paramNames.add("signUser");
            parameters.add('%' + signUser + '%');
            
        }
    }
    
    private void addOrderCodeFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (StringUtils.isNotEmpty(orderCode))
        {
            hql.append(" AND o.code like :orderCode");
            paramNames.add("orderCode");
            parameters.add('%' + orderCode + '%');
            
        }
    }
    
    private void addMarketingCenterFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (StringUtils.isNotEmpty(marketingCenter))
        {
            hql.append(" AND c.marketingCenter = :marketingCenter");
            paramNames.add("marketingCenter");
            parameters.add(marketingCenter);
            
        }
    }
    
    /* private void addCustormerFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
     {
         if (StringUtils.isNotEmpty(custormer))
         {
             hql.append(" AND o.custormer = :custormer");
             paramNames.add("custormer");
             parameters.add(custormer);
         }
     }*/
    
    @Override
    public Class<Order> getEntityClass()
    {
        return Order.class;
    }
    
    public String getContractId()
    {
        return contractId;
    }
    
    public void setContractId(String contractId)
    {
        this.contractId = contractId;
    }
    
    public String getOrderCode()
    {
        return orderCode;
    }
    
    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }
    
    public Integer getMarketingCenter()
    {
        return marketingCenter;
    }
    
    public void setMarketingCenter(Integer marketingCenter)
    {
        this.marketingCenter = marketingCenter;
    }
    
    public String getCustormer()
    {
        return custormer;
    }
    
    public void setCustormer(String custormer)
    {
        this.custormer = custormer;
    }
    
    public String getSignUser()
    {
        return signUser;
    }
    
    public void setSignUser(String signUser)
    {
        this.signUser = signUser;
    }
    
    public Integer getTestingStatus()
    {
        return testingStatus;
    }
    
    public void setTestingStatus(Integer testingStatus)
    {
        this.testingStatus = testingStatus;
    }
    
    public Integer getPaymentStatus()
    {
        return paymentStatus;
    }
    
    public void setPaymentStatus(Integer paymentStatus)
    {
        this.paymentStatus = paymentStatus;
    }
    
}
