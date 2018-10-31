package com.todaysoft.lims.testing.technicalanaly.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.todaysoft.lims.persist.ISearcher;
import com.todaysoft.lims.persist.NamedQueryer;
import com.todaysoft.lims.testing.base.entity.TestingTask;
import com.todaysoft.lims.testing.base.model.TaskSemantic;

public class TechnicalAnalyAssignableTaskSearcher implements ISearcher<Object[]>
{
    private String sequencingCode;
    
    private String marketingCenter;
    
    private Set<String> includeKeys;
    
    private String contractCode;
    
    private String testProduct;
    
    private String customerName;
    
    private String isPartaked;
    
    private String subUnit;
    
    private String technicalDirector;
    
    private Integer ifUrgent;
    
    public String getProductDuty()
    {
        return productDuty;
    }
    
    public void setProductDuty(String productDuty)
    {
        this.productDuty = productDuty;
    }
    
    private String productDuty;
    
    @Override
    public NamedQueryer toQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        List<String> names = new ArrayList<String>();
        List<Object> values = new ArrayList<Object>();
        
        hql.append("SELECT t.id,t.testingLaneCode,max(t.plannedFinishDate), max(t.ifUrgent),t.startTime FROM TestingTask t LEFT JOIN t.inputSample.receivedSample as sample LEFT JOIN sample.order as order, TechnicalAnalyTestingTask tatt");
        hql.append(" WHERE t.semantic = :semantic AND t.status = :assignableStatus AND t.id = tatt.taskId");
        hql.append(" AND EXISTS (SELECT sa.id FROM TestingScheduleActive sa WHERE sa.taskId = t.id and sa.schedule.proccessState = 0");
        
        names.add("semantic");
        values.add(TaskSemantic.TECHNICAL_ANALY);
        
        names.add("assignableStatus");
        values.add(TestingTask.STATUS_ASSIGNABLE);
        
        if (!StringUtils.isEmpty(marketingCenter) || !StringUtils.isEmpty(contractCode) || !StringUtils.isEmpty(customerName)
            || !StringUtils.isEmpty(isPartaked) || !StringUtils.isEmpty(subUnit))
        {
            hql.append(" AND EXISTS (SELECT o.id FROM Order o WHERE sa.schedule.orderId = o.id");
            
            if (!StringUtils.isEmpty(marketingCenter))
            {
                hql.append(" AND o.type.id = :marketingCenter");
                names.add("marketingCenter");
                values.add(marketingCenter);
            }
            
            if (!StringUtils.isEmpty(contractCode))
            {
                hql.append(" AND o.contract.code LIKE :contractCode");
                names.add("contractCode");
                values.add(contractCode + "%");
            }
            
            if (!StringUtils.isEmpty(customerName))
            {
                hql.append(" AND EXISTS(SELECT c.id FROM Customer c WHERE c.realName LIKE :customerName AND c.id = o.ownerId)");
                names.add("customerName");
                values.add(customerName + "%");
            }
            
            if (!StringUtils.isEmpty(isPartaked))
            {
                hql.append(" AND o.doctorAssist = :isPartaked");
                names.add("isPartaked");
                values.add(isPartaked);
            }
            
            if (!StringUtils.isEmpty(subUnit))
            {
                hql.append(" AND EXISTS(SELECT c.id FROM Customer c WHERE c.company.name LIKE :subUnit AND c.id = o.ownerId)");
                names.add("subUnit");
                values.add(subUnit + "%");
            }
            
            hql.append(")");
        }
        
        if (!StringUtils.isEmpty(testProduct) || !StringUtils.isEmpty(technicalDirector) || com.todaysoft.lims.utils.StringUtils.isNotEmpty(productDuty))
        {
            hql.append(" AND EXISTS (SELECT p.id FROM Product p WHERE sa.schedule.productId = p.id AND sa.schedule.verifyKey IS NULL");
            
            if (!StringUtils.isEmpty(testProduct))
            {
                hql.append(" AND p.name LIKE :products");
                names.add("products");
                values.add(testProduct + "%");
            }
            
            if (!StringUtils.isEmpty(technicalDirector))
            {
                hql.append(" AND EXISTS (SELECT pp.id FROM ProductPrincipal pp WHERE pp.product.id = p.id AND pp.principal.archive.name LIKE :technicalDirector)");
                names.add("technicalDirector");
                values.add(technicalDirector + "%");
            }
            
            if (!StringUtils.isEmpty(productDuty))
            {
                hql.append(" AND p.productDuty.archive.name LIKE :productDuty");
                names.add("productDuty");
                values.add(productDuty + "%");
            }
            
            hql.append(")");
        }
        
        hql.append(")");
        
        if (!StringUtils.isEmpty(sequencingCode))
        {
            hql.append(" AND tatt.sequencingCode = :sequencingCode");
            names.add("sequencingCode");
            values.add(sequencingCode);
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
            if (1 != ifUrgent)
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
        
        hql.append(" GROUP BY t.testingLaneCode ORDER BY t.ifUrgent DESC,t.resubmitCount DESC, order.code, sample.sampleCode");
        
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(names);
        queryer.setValues(values);
        return queryer;
    }
    
    @Override
    public Class<Object[]> getEntityClass()
    {
        return Object[].class;
    }
    
    public String getSequencingCode()
    {
        return sequencingCode;
    }
    
    public void setSequencingCode(String sequencingCode)
    {
        this.sequencingCode = sequencingCode;
    }
    
    public String getMarketingCenter()
    {
        return marketingCenter;
    }
    
    public void setMarketingCenter(String marketingCenter)
    {
        this.marketingCenter = marketingCenter;
    }
    
    public Set<String> getIncludeKeys()
    {
        return includeKeys;
    }
    
    public void setIncludeKeys(Set<String> includeKeys)
    {
        this.includeKeys = includeKeys;
    }
    
    public String getContractCode()
    {
        return contractCode;
    }
    
    public void setContractCode(String contractCode)
    {
        this.contractCode = contractCode;
    }
    
    public String getTestProduct()
    {
        return testProduct;
    }
    
    public void setTestProduct(String testProduct)
    {
        this.testProduct = testProduct;
    }
    
    public String getCustomerName()
    {
        return customerName;
    }
    
    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }
    
    public String getIsPartaked()
    {
        return isPartaked;
    }
    
    public void setIsPartaked(String isPartaked)
    {
        this.isPartaked = isPartaked;
    }
    
    public String getSubUnit()
    {
        return subUnit;
    }
    
    public void setSubUnit(String subUnit)
    {
        this.subUnit = subUnit;
    }
    
    public String getTechnicalDirector()
    {
        return technicalDirector;
    }
    
    public void setTechnicalDirector(String technicalDirector)
    {
        this.technicalDirector = technicalDirector;
    }
    
    public Integer getIfUrgent()
    {
        return ifUrgent;
    }
    
    public void setIfUrgent(Integer ifUrgent)
    {
        this.ifUrgent = ifUrgent;
    }
    
    public NamedQueryer toDetailQuery()
    {
        StringBuffer hql = new StringBuffer(512);
        List<String> names = new ArrayList<String>();
        List<Object> values = new ArrayList<Object>();
        
        hql.append("SELECT t, tatt FROM TestingTask t LEFT JOIN t.inputSample.receivedSample as sample LEFT JOIN sample.order as order, TechnicalAnalyTestingTask tatt");
        hql.append(" WHERE t.semantic = :semantic AND t.status = :assignableStatus AND t.id = tatt.taskId");
        hql.append(" AND EXISTS (SELECT sa.id FROM TestingScheduleActive sa WHERE sa.taskId = t.id and sa.schedule.proccessState = 0");
        
        names.add("semantic");
        values.add(TaskSemantic.TECHNICAL_ANALY);
        
        names.add("assignableStatus");
        values.add(TestingTask.STATUS_ASSIGNABLE);
        
        if (!StringUtils.isEmpty(marketingCenter) || !StringUtils.isEmpty(contractCode) || !StringUtils.isEmpty(customerName)
            || !StringUtils.isEmpty(isPartaked) || !StringUtils.isEmpty(subUnit))
        {
            hql.append(" AND EXISTS (SELECT o.id FROM Order o WHERE sa.schedule.orderId = o.id");
            
            if (!StringUtils.isEmpty(marketingCenter))
            {
                hql.append(" AND o.type.id = :marketingCenter");
                names.add("marketingCenter");
                values.add(marketingCenter);
            }
            
            if (!StringUtils.isEmpty(contractCode))
            {
                hql.append(" AND o.contract.code LIKE :contractCode");
                names.add("contractCode");
                values.add(contractCode + "%");
            }
            
            if (!StringUtils.isEmpty(customerName))
            {
                hql.append(" AND EXISTS(SELECT c.id FROM Customer c WHERE c.realName LIKE :customerName AND c.id = o.ownerId)");
                names.add("customerName");
                values.add(customerName + "%");
            }
            
            if (!StringUtils.isEmpty(isPartaked))
            {
                hql.append(" AND o.doctorAssist = :isPartaked");
                names.add("isPartaked");
                values.add(isPartaked);
            }
            
            if (!StringUtils.isEmpty(subUnit))
            {
                hql.append(" AND EXISTS(SELECT c.id FROM Customer c WHERE c.company.name LIKE :subUnit AND c.id = o.ownerId)");
                names.add("subUnit");
                values.add(subUnit + "%");
            }
            
            hql.append(")");
        }
        
        if (!StringUtils.isEmpty(testProduct) || !StringUtils.isEmpty(technicalDirector) || com.todaysoft.lims.utils.StringUtils.isNotEmpty(productDuty))
        {
            hql.append(" AND EXISTS (SELECT p.id FROM Product p WHERE sa.schedule.productId = p.id AND sa.schedule.verifyKey IS NULL");
            
            if (!StringUtils.isEmpty(testProduct))
            {
                hql.append(" AND p.name LIKE :products");
                names.add("products");
                values.add(testProduct + "%");
            }
            
            if (!StringUtils.isEmpty(technicalDirector))
            {
                hql.append(" AND EXISTS (SELECT pp.id FROM ProductPrincipal pp WHERE pp.product.id = p.id AND pp.principal.archive.name LIKE :technicalDirector)");
                names.add("technicalDirector");
                values.add(technicalDirector + "%");
            }
            
            if (!StringUtils.isEmpty(productDuty))
            {
                hql.append(" AND p.productDuty.archive.name LIKE :productDuty");
                names.add("productDuty");
                values.add(productDuty + "%");
            }
            
            hql.append(")");
        }
        
        hql.append(")");
        
        if (!StringUtils.isEmpty(sequencingCode))
        {
            hql.append(" AND tatt.sequencingCode = :sequencingCode");
            names.add("sequencingCode");
            values.add(sequencingCode);
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
            if (1 != ifUrgent)
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
        
        hql.append("  ORDER BY t.ifUrgent DESC,t.resubmitCount DESC, order.code, sample.sampleCode");
        
        NamedQueryer queryer = new NamedQueryer();
        queryer.setHql(hql.toString());
        queryer.setNames(names);
        queryer.setValues(values);
        return queryer;
    }
    
}
