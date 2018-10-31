package com.todaysoft.lims.testing.extrasample.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;

public class ExtraSampleTaskSearcher implements ISearcher<Object[]>
{
    private String marketingCenter;
    
    private String sampleCode;
    
    private Integer purpose;
    
    private String orderCode;
    
    private Integer status;

    private String projectManager;
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        List<String> names = new ArrayList<String>();
        List<Object> values = new ArrayList<Object>();
        
        hql.append("FROM OrderExtraSampleHandle oes, Order o,ReceivedSample r WHERE oes.orderId = o.id AND oes.sampleId = r.sampleId");
        addIsHandleFilter(hql, names, values);
        addPrjManagerFilter(hql, names, values);
        if (!StringUtils.isEmpty(marketingCenter))
        {
            hql.append(" AND o.type.id = :marketingCenter");
            names.add("marketingCenter");
            values.add(marketingCenter);
        }
        
        if (!StringUtils.isEmpty(sampleCode))
        {
            hql.append(" AND r.sampleCode = :sampleCode");
            names.add("sampleCode");
            values.add(sampleCode);
        }
        
        if (!StringUtils.isEmpty(orderCode))
        {
            hql.append(" AND o.code LIKE :orderCode");
            names.add("orderCode");
            values.add("%" + orderCode + "%");
        }
        
        if (null != purpose)
        {
            hql.append(" AND r.purpose = :purpose");
            names.add("purpose");
            values.add(String.valueOf(purpose));
        }
        
        hql.append(" ORDER BY oes.createTime DESC");
        
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(names);
        queryer.setValues(values);
        return queryer;
    }
    
    private void addIsHandleFilter(StringBuffer hql, List<String> names, List<Object> values)
    {
        if (!StringUtils.isEmpty(status))
        {
            hql.append(" AND oes.status = :status");
            names.add("status");
            values.add(status);
        }
    }

    private void addPrjManagerFilter(StringBuffer hql, List<String> paramNames, List<Object> parameters)
    {
        if (!com.todaysoft.lims.utils.StringUtils.isEmpty(projectManager))
        {
            hql.append(" AND o.projectManager = :projectManager");
            paramNames.add("projectManager");
            parameters.add(projectManager);
        }

    }
    @Override
    public Class<Object[]> getEntityClass()
    {
        return Object[].class;
    }
    
    public String getMarketingCenter()
    {
        return marketingCenter;
    }
    
    public void setMarketingCenter(String marketingCenter)
    {
        this.marketingCenter = marketingCenter;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public Integer getPurpose()
    {
        return purpose;
    }
    
    public void setPurpose(Integer purpose)
    {
        this.purpose = purpose;
    }
    
    public String getOrderCode()
    {
        return orderCode;
    }
    
    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }
    
    public Integer getStatus()
    {
        return status;
    }
    
    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public String getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(String projectManager) {
        this.projectManager = projectManager;
    }
}
