package com.todaysoft.lims.testing.dnaqc.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.util.StringUtils;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.testing.base.entity.TestingTask;

public class DNAQcTaskSearcher implements ISearcher<TestingTask>
{
    private String orderType;
    
    private String sampleCode;
    
    private String sampleName;
    
    private String products;
    
    private Boolean resubmit;
    
    private String originalSampleTypeName;
    
    private Set<String> includeKeys;
    
    private Integer ifUrgent;
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        List<String> names = new ArrayList<String>();
        List<Object> values = new ArrayList<Object>();
        
        hql.append("FROM TestingTask t WHERE t.semantic = 'DNA-QC' AND t.status = :assignableStatus");
        hql.append(" AND EXISTS (SELECT sa.id FROM TestingScheduleActive sa WHERE sa.taskId = t.id and sa.schedule.proccessState = 0");
        
        names.add("assignableStatus");
        values.add(TestingTask.STATUS_ASSIGNABLE);
        
        if (!StringUtils.isEmpty(orderType))
        {
            hql.append(" AND EXISTS (SELECT o.id FROM Order o WHERE sa.schedule.orderId = o.id AND o.type.id = :orderType)");
            names.add("orderType");
            values.add(orderType);
        }
        
        if (!StringUtils.isEmpty(products))
        {
            hql.append(
                " AND EXISTS (SELECT p.id FROM Product p WHERE sa.schedule.productId = p.id AND p.name LIKE :products AND sa.schedule.verifyKey IS NULL)");
            names.add("products");
            values.add(products + "%");
        }
        
        hql.append(")");
        
        if (!StringUtils.isEmpty(sampleCode))
        {
            hql.append(" AND t.receivedSampleCode LIKE :sampleCode");
            names.add("sampleCode");
            values.add(sampleCode + "%");
        }
        
        if (!StringUtils.isEmpty(originalSampleTypeName))
        {
            hql.append(" AND t.inputSample.sampleTypeName LIKE :originalSampleTypeName");
            names.add("originalSampleTypeName");
            values.add(originalSampleTypeName + "%");
        }
        
        if (!StringUtils.isEmpty(sampleName))
        {
            hql.append(" AND t.inputSample.receivedSample.sampleName LIKE :sampleName");
            names.add("sampleName");
            values.add(sampleName + "%");
        }
        
        if (!StringUtils.isEmpty(resubmit))
        {
            hql.append(" AND t.resubmit = :resubmit");
            names.add("resubmit");
            values.add(resubmit);
        }
        
        if (null != includeKeys)
        {
            if (includeKeys.isEmpty())
            {
                hql.append(" AND 1 = 2");
            }
            else
            {
                hql.append(" AND t.id IN (:includeKeys)");
                names.add("includeKeys");
                values.add(includeKeys);
            }
        }
        
        if (null != ifUrgent)
        {
            if(1 != ifUrgent)
            {
                hql.append(" AND (t.ifUrgent != 1 or t.ifUrgent is null)");
            }
            else
            {
                hql.append(" AND t.ifUrgent = :ifUrgent ");
                names.add("ifUrgent");
                values.add(ifUrgent);
            }
        }
        
        hql.append(" ORDER BY t.ifUrgent DESC,t.inputSample.sampleTypeId, t.resubmitCount DESC");
        
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(names);
        queryer.setValues(values);
        return queryer;
    }
    
    @Override
    public Class<TestingTask> getEntityClass()
    {
        return TestingTask.class;
    }
    
    public String getOrderType()
    {
        return orderType;
    }
    
    public void setOrderType(String orderType)
    {
        this.orderType = orderType;
    }
    
    public String getSampleCode()
    {
        return sampleCode;
    }
    
    public void setSampleCode(String sampleCode)
    {
        this.sampleCode = sampleCode;
    }
    
    public String getSampleName()
    {
        return sampleName;
    }
    
    public void setSampleName(String sampleName)
    {
        this.sampleName = sampleName;
    }
    
    public String getProducts()
    {
        return products;
    }
    
    public void setProducts(String products)
    {
        this.products = products;
    }
    
    public Boolean getResubmit()
    {
        return resubmit;
    }
    
    public void setResubmit(Boolean resubmit)
    {
        this.resubmit = resubmit;
    }
    
    public Set<String> getIncludeKeys()
    {
        return includeKeys;
    }
    
    public void setIncludeKeys(Set<String> includeKeys)
    {
        this.includeKeys = includeKeys;
    }
    
    public String getOriginalSampleTypeName()
    {
        return originalSampleTypeName;
    }
    
    public void setOriginalSampleTypeName(String originalSampleTypeName)
    {
        this.originalSampleTypeName = originalSampleTypeName;
    }

    public Integer getIfUrgent()
    {
        return ifUrgent;
    }

    public void setIfUrgent(Integer ifUrgent)
    {
        this.ifUrgent = ifUrgent;
    }
    
}
